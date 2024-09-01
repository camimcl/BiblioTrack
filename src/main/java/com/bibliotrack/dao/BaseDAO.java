package com.bibliotrack.dao;

import com.bibliotrack.database.MySQLConnection;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

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
            //USA A CONEXAO PARA CRIAR UM OBJETO DO TIPO DSLCONTEXT PARA REALIZAR CONSULTAS SQL
            DSLContext create = DSL.using(connection);

            return function.apply(create);
        }
    }

    public T add(T entity) throws SQLException {
        return execute((create) -> {
            // Obtém os campos da entidade usando reflection
            java.lang.reflect.Field[] fields = entity.getClass().getDeclaredFields();

            // Mapeia os campos da entidade para campos do jOOQ
            Field<?>[] jooqFields = Arrays.stream(fields)
                    .map(field -> DSL.field(DSL.name(field.getName())))
                    .toArray(Field[]::new);

            // Obtém os valores dos campos da entidade
            Object[] values = Arrays.stream(fields)
                    .peek(field -> field.setAccessible(true))
                    .map(field -> {
                        try {
                            Object value = field.get(entity);
                            if (value instanceof Enum) {
                                return ((Enum<?>) value).name();
                            }
                            return value;
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray();

            // Define o nome do campo de ID que precisa ser retornado pelo banco
            String idFieldName = "id"; // Substitua pelo nome correto do campo de ID
            Field<Integer> idField = DSL.field(DSL.name(idFieldName), Integer.class);

            // Tente realizar o insert e capturar o ID gerado
            try {
                // Realiza o insert e retorna o registro com o ID gerado
                Record record = create.insertInto(getTable(), jooqFields)
                        .values(values)
                        .returning(idField) // Retorna o campo de ID especificado
                        .fetchOne();

                // Verifica se o registro retornado não é nulo e se o ID gerado está correto
                if (record != null) {
                    Integer generatedId = record.get(idField);
                    if (generatedId != null) {
                        try {
                            // Configura o ID gerado na entidade
                            java.lang.reflect.Field entityIdField = entity.getClass().getDeclaredField(idFieldName);
                            entityIdField.setAccessible(true);
                            entityIdField.set(entity, generatedId);
                            System.out.println("ID gerado: " + generatedId); // Diagnóstico
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            throw new RuntimeException("Erro ao definir o ID gerado automaticamente na entidade.", e);
                        }
                    } else {
                        System.out.println("ID gerado foi nulo."); // Diagnóstico
                    }
                } else {
                    System.out.println("Falha ao inserir e capturar o ID gerado."); // Diagnóstico
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erro ao executar o insert e capturar o ID: " + e.getMessage()); // Diagnóstico adicional
            }

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
