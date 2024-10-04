package com.bibliotrack.dao;

import com.bibliotrack.entities.User;

import java.sql.SQLException;
import java.util.List;

import com.bibliotrack.services.UserService;
import org.jooq.impl.DSL;

public class UserDAO extends BaseDAO<User> {


    @Override
    protected String getTableName() {
        return "User";
    }
    public User addUser(User user) throws SQLException {
        return add(user);
    }
    public void removeUser(int id) throws SQLException {
        remove("id", id);
    }
    public User editUser(User user) throws SQLException {
        int originalId = user.getId();

        // Verificar se a senha foi alterada
        User existingUser = findUserById(originalId);
        if (existingUser != null && !existingUser.getPassword().equals(user.getPassword())) {
            // A senha foi modificada, então aplique o hash novamente
            String hashedPassword = UserService.getInstance().hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
        }

        return edit(user, "id", originalId);
    }


    public User findUserById(int id) throws SQLException {
       return findByIdentityField(id, User.class);
    }
    public List <User> findUserByName(String userName) throws SQLException {
        List <User> users = find("name",userName,User.class);
        return users.isEmpty() ? null : users;
    }
    public User findUserByEmail(String email) throws SQLException {
        List <User> users = find("email",email,User.class);
        return users.isEmpty() ? null : users.get(0);
    }

}
