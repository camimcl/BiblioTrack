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

    }

    public User addUser(User user) throws SQLException {
        return add(user);
    }
    public void removeUser(int id) throws SQLException {
        remove("id", id);
    }
    public User editUser(User user) throws SQLException {
        return edit(user, "id", user.getId());
    }
    public User findUserById(int id) throws SQLException {
        List <User> users = find("id",id,User.class);
        return users.isEmpty() ? null : users.get(0);
    }
    public User findUserByName(String userName) throws SQLException {
        List <User> users = find("name",userName,User.class);
        return users.isEmpty() ? null : users.get(0);
    }

}
