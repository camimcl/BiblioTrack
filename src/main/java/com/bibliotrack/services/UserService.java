package com.bibliotrack.services;

import com.bibliotrack.dao.UserDAO;
import com.bibliotrack.entities.User;
import com.bibliotrack.enums.Role;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public boolean isEmailValid(String email) {
        return email != null && email.contains("@");
    }

    public boolean isPasswordStrong(String password) {
        return password != null && password.length() >= 8 && password.matches(".*\\d.*");
    }

    public boolean isEmailDuplicate(String email) throws SQLException {
        return userDAO.findUserByEmail(email) != null;
    }
    public boolean validateUser(User user) throws SQLException {
        if (!isEmailValid(user.getEmail())) {
            System.out.println("Invalid email.");
            return false;
        }
        if (!isPasswordStrong(user.getPassword())) {
            System.out.println("Weak password.");
            return false;
        }
        if (isEmailDuplicate(user.getEmail())) {
            System.out.println("Email already exists.");
            return false;
        }
        return true;
    }

    public User registerUser(User user) throws SQLException {

        if (validateUser(user)) {
            String hashedPassword = hashPassword(user.getPassword());
            user.setPassword(hashedPassword);

            return userDAO.addUser(user);
        }

        return null;
    }
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public boolean authenticateUser(String email, String password) throws SQLException {
        User user = userDAO.findUserByEmail(email);

        if(user == null){
            return false;
        }
        //verify password
        return BCrypt.checkpw(password, user.getPassword());
    }

    public boolean hasRole(User user, Role role) {
        return user.getRole().equals(role);
    }
}
