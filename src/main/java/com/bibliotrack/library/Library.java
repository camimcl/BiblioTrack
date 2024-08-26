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
       User user = userService.getUserById(1);

       userService.add(user);

       System.out.println(user.getName());
//       MySQLConnection mySQLConnection = new MySQLConnection();
//
//       Connection connection = mySQLConnection.getConnection();
//       try {
//           System.out.println("Connected to the database successfully!");
//
//           // 3. Create a statement
//           Statement statement = connection.createStatement();
//
//           // 4. Execute a query
//           String query = "SELECT * FROM User";
//           ResultSet resultSet = statement.executeQuery(query);
//
//           // 5. Process the result set
//           while (resultSet.next()) {
//               System.out.println("Column1: " + resultSet.getString("name"));
//               System.out.println("Column2: " + resultSet.getInt("id"));
//           }
//
//           // 6. Close the result set, statement, and connection
//           resultSet.close();
//           statement.close();
//       } catch (SQLException e) {
//           e.printStackTrace();
//       } finally {
//           // 7. Ensure the connection is closed
//           if (connection != null) {
//               try {
//                   connection.close();
//               } catch (SQLException e) {
//                   e.printStackTrace();
//               }
//           }
//       }
   }
}
