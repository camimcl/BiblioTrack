package com.bibliotrack.dao;

import com.bibliotrack.annotations.util.AnnotationUtil;
import com.bibliotrack.database.MySQLConnection;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class BaseDAO<T> {

    protected Table<Record> getTable() {
        return DSL.table(getTableName());
    }

    protected abstract String getTableName();

    protected <R> R execute(Function<DSLContext, R> function) throws SQLException {
        try(Connection connection = MySQLConnection.getConnection()) {
            Configuration configuration = new DefaultConfiguration();

            configuration = configuration.set(SQLDialect.MYSQL);
            configuration = configuration.set(connection);

            Settings settings = new Settings();
            settings.setExecuteLogging(true);

            configuration = configuration.set(settings);

            DSLContext create = DSL.using(configuration);

            return function.apply(create);
        }
    }

    public T add(T entity) throws SQLException {
        // TODO: use object mapper
        return execute((create) -> {
            // Get the fields of the entity using reflection
            java.lang.reflect.Field[] fields = entity.getClass().getDeclaredFields();

            // Map the fields to JOOQ DSL fields
            Field<?>[] jooqFields = Arrays.stream(fields)
                    .map(field -> DSL.field(DSL.name(field.getName())))
                    .toArray(Field[]::new);

            // Map the values from the entity to an array
            Object[] values = Arrays.stream(fields)
                    .peek(field -> field.setAccessible(true))
                    .map(field -> {
                        try {
                            Object value = field.get(entity);
                            if(value instanceof Enum) {
                                return ((Enum<?>) value).name();
                            }
                            return value;
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray();

            create.insertInto(getTable(), jooqFields)
                    .values(values)
                    .execute();

            try {
                return findByIdentityField(create.lastID(), (Class<T>) entity.getClass());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public T edit(T entity, String idField, Object idValue) throws SQLException {
        return execute((create) -> {
            // Obtenha os campos da entidade usando reflection
            java.lang.reflect.Field[] fields = entity.getClass().getDeclaredFields();

            // Crie um Map para armazenar os pares campo/valor a serem atualizados
            Map<Field<?>, Object> fieldMap = new HashMap<>();

            // Popule o Map com os valores da entidade, excluindo o campo ID
            Arrays.stream(fields)
                    .filter(field -> !field.getName().equals(idField)) // Exclui o campo ID
                    .peek(field -> field.setAccessible(true))
                    .forEach(field -> {
                        try {
                            Object value = field.get(entity);
                            if (value instanceof Enum) {
                                // Converte o enum para o valor String correspondente
                                value = ((Enum<?>) value).name();
                            }
                            // Adiciona o campo e o valor (convertido se for enum) ao Map
                            fieldMap.put(DSL.field(DSL.name(field.getName())), value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });

            // Realiza o update
            create.update(getTable())
                    .set(fieldMap)
                    .where(DSL.field(idField).eq(idValue))
                    .execute();

            return entity;
        });
    }



    public void remove(String idField, Object idValue) throws SQLException {
        execute((create) -> {
            create.deleteFrom(getTable())
                    .where(DSL.field(idField).eq(idValue))
                    .execute();
            return null;
        });
    }

    public T findByIdentityField(Object value, Class<T> type) throws SQLException {
        return execute((create) -> {
            List<T> result = create.select()
                    .from(getTable())
                    .where(
                        DSL.field(AnnotationUtil.getIdentityFieldName(type))
                       .eq(value)
                    ).fetchInto(type);

            if(result.isEmpty()) {
                return null;
            }

            return result.get(0);
        });
    }

    public List<T> find(String fieldName, Object value, Class<T> type) throws SQLException {
        return execute((create) -> {
            return create.select()
                    .from(getTable())
                    .where(DSL.field(fieldName).eq(value))
                    .fetchInto(type);
        });
    }
    public void editField(String fieldName, Object fieldValue, String idField, Object idValue) throws SQLException {
        execute((create) -> {
            create.update(getTable())
                    .set(DSL.field(fieldName), fieldValue)
                    .where(DSL.field(idField).eq(idValue))
                    .execute();
            return null;
        });
    }
}
