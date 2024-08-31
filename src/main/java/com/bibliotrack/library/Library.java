package com.bibliotrack.library;

import com.bibliotrack.entities.User;
import com.bibliotrack.dao.UserDAO;
import com.bibliotrack.enums.Role;

import java.sql.SQLException;

public class Library {
   //login
   public static void main(String[] args) throws SQLException {
       UserDAO userDAO = new UserDAO();
//       User user = new User(123, "thiago buarque", "thiago123@gmailcom", "thiago9595", Role.ADMIN);
       try {
//           userDAO.addUser(user);
           System.out.println(userDAO.findUserById(123));

       }
       catch (SQLException e) {
           e.printStackTrace();
           System.out.println("Erro ao adicionar o usuário.");
       }


   }
}
