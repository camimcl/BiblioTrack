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
//       User camile = new User(5,"Camile Pereira","camilep@gmail.com","olamundo1236");
//
//       userService.add(camile);

       System.out.println(userService.findUserById(2));
       System.out.println(userService.findUserByName("Pedro"));

   }
}
