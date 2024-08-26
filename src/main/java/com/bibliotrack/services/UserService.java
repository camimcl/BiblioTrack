package com.bibliotrack.services;

import com.bibliotrack.database.MySQLConnection;
import com.bibliotrack.entities.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;

public class UserService extends BaseService<User> {

    @Override
    protected String getTableName() {
        return "User";
    }

    public User getUserById(int id) throws SQLException {
        return execute((create) -> {
            List<User> result = create.select()
                    .from("User")
                    .where(DSL.field("id").eq(id))
                    .fetchInto(User.class);

            if (!result.isEmpty()) {
                return result.get(0);
            }

            return null;
        });
    }
//        try(Connection connection = MySQLConnection.getConnection()) {
//            DSLContext create = DSL.using(connection);
//
//            List<User> result = create.select()
//                                    .from("User")
//                                    .where(DSL.field("id").eq(id))
//                                    .fetchInto(User.class);
//
//            if (!result.isEmpty()) {
//                return result.get(0);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;

    public List<User> getUserbyName(String name) throws SQLException {
        return execute((create) -> {
            List <User> result = create.select()
                    .from("User")
                    .where(DSL.field("name")
                            .eq(name))
                    .fetchInto(User.class);

            if(!result.isEmpty()) {
                return result;
            }

            return null;
        });
//        try (Connection connection = MySQLConnection.getConnection()) {
//            DSLContext create = DSL.using(connection);
//
//            List <User> result = create.select()
//                                    .from("User")
//                                    .where(DSL.field("name")
//                                    .eq(name))
//                                    .fetchInto(User.class);
//            if(!result.isEmpty()) {
//                return result;
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }
//    public User addUser(User user) throws SQLException {
//        return add(user);
//        try (Connection connection = MySQLConnection.getConnection()) {
//            DSLContext create = DSL.using(connection);
//
//            create.insertInto(DSL.table("User"),
//                    DSL.field("id"),
//                    DSL.field("name"),
//                    DSL.field("email"),
//                    DSL.field("password"))
//
//                    .values(user.getId(),
//                            user.getName(),
//                            user.getEmail(),
//                            user.getPassword())
//                    .execute();
//
//            return user;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//            return null;
//    }
    public User editUser (User user) {
        try (Connection connection = MySQLConnection.getConnection()){
            DSLContext create = DSL.using(connection);

            create.update(DSL.table("User"))
                    .set(DSL.field("email"),user.getEmail())
                    .set(DSL.field("password"),user.getPassword());

            return user;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void removeUser(int id) {
        try (Connection connection = MySQLConnection.getConnection()){
            DSLContext create = DSL.using(connection);

            create.deleteFrom(DSL.table("User")
                    .where(DSL.field("id")
                    .eq(id)));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
