package com.bibliotrack.services;

import com.bibliotrack.database.MySQLConnection;
import com.bibliotrack.entities.User;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class BaseService<T> {

    protected Table<Record> getTable() {
        return DSL.table(getTableName());
    }

    protected abstract String getTableName();

    protected <R> R execute(Function<DSLContext, R> function) throws SQLException {
        try(Connection connection = MySQLConnection.getConnection()) {
            //USA A CONEXAO PARA CRIAR UM OBJETO DO TIPO DSLCONTEXT PARA REALIZAR CONSULTAS SQL
            DSLContext create = DSL.using(connection);

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
                            return field.get(entity);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray();

            // Perform the insert
            create.insertInto(getTable(), jooqFields)
                    .values(values)
                    .execute();

            return entity;
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
