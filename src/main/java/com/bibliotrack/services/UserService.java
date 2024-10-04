package com.bibliotrack.services;

import com.bibliotrack.dao.UserDAO;
import com.bibliotrack.entities.User;
import com.bibliotrack.enums.Role;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private static UserService instance;
    private UserDAO userDAO = new UserDAO();

    private UserService(){
    }
    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

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
    public User editUser(User user) throws SQLException {
        // Recupera o usuário existente no banco de dados
        User existingUser = userDAO.findUserById(user.getId());

        if (existingUser == null) {
            throw new SQLException("Usuário não encontrado.");
        }

        // Verifica se o email foi alterado e valida o novo email
        if (!existingUser.getEmail().equals(user.getEmail())) {
            if (!isEmailValid(user.getEmail())) {
                throw new SQLException("Email inválido.");
            }
            if (isEmailDuplicate(user.getEmail())) {
                throw new SQLException("Email já em uso.");
            }
        }

        // Verifica se a senha foi alterada
        if (!existingUser.getPassword().equals(user.getPassword())) {
            if (!isPasswordStrong(user.getPassword())) {
                throw new SQLException("Senha fraca.");
            }
            // Aplica o hash na nova senha antes de salvar
            user.setPassword(hashPassword(user.getPassword()));
        }

        // Chama o DAO para editar o usuário no banco
        return userDAO.editUser(user);
    }
}
