package com.bibliotrack.dao;

import com.bibliotrack.annotations.util.AnnotationUtil;
import com.bibliotrack.database.MySQLConnection;
import jakarta.xml.bind.JAXB;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.Configuration;
import org.jooq.meta.jaxb.Logging;

import java.io.File;
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
            //DSLContext create = DSL.using(connection);
            Configuration configuration = new DefaultConfiguration();
//            configuration.withLogging(Logging.WARN);
            configuration.set(SQLDialect.MYSQL);
            configuration.set(connection);
            configuration.set(new Settings().withExecuteLogging(true));

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

            String identityFieldName = AnnotationUtil.getIdentityFieldName(entity.getClass());

            // Perform the insert
            Record record = create.insertInto(getTable(), jooqFields)
                    .values(values)
                    .returning(DSL.field(identityFieldName))
                    .fetchOne();

            try {
                return findByIdentityField(record.get(identityFieldName), (Class<T>) entity.getClass());
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
                            // Adiciona apenas campos que não sejam o campo ID
                            fieldMap.put(DSL.field(DSL.name(field.getName())), field.get(entity));
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
        return execute((create) -> create.select()
                    .from(getTable())
                    .where(DSL.field(AnnotationUtil.getIdentityFieldName(type)).eq(value))
                    .fetchInto(type).get(0));
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
