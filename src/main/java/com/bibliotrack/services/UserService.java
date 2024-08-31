package com.bibliotrack.services;

import com.bibliotrack.dao.UserDAO;
import com.bibliotrack.entities.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public void registerUser(User user) throws SQLException {
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        userDAO.addUser(user);
    }
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public boolean authenticateUser(String email, String password) throws SQLException {
        User user = userDAO.findUserByEmail(email);

        //user not found
        if(user == null){
            return false;
        }
        //verify password
        return BCrypt.checkpw(password, user.getPassword());
    }
}
