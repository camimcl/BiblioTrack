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
import java.util.List;
import java.util.function.Function;

public abstract class BaseService<T> {

    protected Table<Record> getTable() {
        return DSL.table(getTableName());
    }

    protected abstract String getTableName();

    protected <R> R execute(Function<DSLContext, R> function) throws SQLException {
        try(Connection connection = MySQLConnection.getConnection()) {
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
}
