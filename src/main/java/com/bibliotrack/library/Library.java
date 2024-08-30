package com.bibliotrack.library;

import com.bibliotrack.database.MySQLConnection;
import com.bibliotrack.entities.User;
import com.bibliotrack.services.UserService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Library {
   //login
   public static void main(String[] args) throws SQLException {
       UserService userService = new UserService();
//
//       userService.addUser(camile);
       try {
        User test = userService.findUserById(3);
//        System.out.println(test);

            if (test  != null) {
//                User pedro = new User(8,"Pedro Araujo","pedropicanhasmppp@gmail.com","olamundo1236");
//                 test.setEmail("thiaguinhooo55553@gmail.com");
//                userService.addUser(pedro);
//                userService.editUser(test);
//                System.out.println(test);
//                userService.removeUser();
//                System.out.println(userService.findUserByName("Camile"));
                System.out.println(userService.findUserByName("Pedro Araujo"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao editar o usuário.");
       }




   }
}
