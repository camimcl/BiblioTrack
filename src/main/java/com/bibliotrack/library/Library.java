package com.bibliotrack.library;

import com.bibliotrack.dao.BookDAO;
import com.bibliotrack.dao.BorrowDAO;
import com.bibliotrack.dao.FineDAO;
import com.bibliotrack.entities.Book;
import com.bibliotrack.entities.Borrow;
import com.bibliotrack.entities.Fine;
import com.bibliotrack.entities.User;
import com.bibliotrack.dao.UserDAO;
import com.bibliotrack.enums.Role;
import com.bibliotrack.services.BookService;
import com.bibliotrack.services.BorrowService;
import com.bibliotrack.services.FineService;
import com.bibliotrack.services.UserService;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Library {
   //login
   public static void main(String[] args) throws SQLException {
       try {
           // Instanciando DAOs e Services
           UserDAO userDAO = new UserDAO();
           UserService userService = new UserService();
           BookDAO bookDAO = new BookDAO();
           BookService bookService = new BookService();
           BorrowDAO borrowDAO = new BorrowDAO();
           BorrowService borrowService = new BorrowService();
           FineDAO fineDAO = new FineDAO();
           FineService fineService = new FineService();

            User user ;
            User user2 ;
            User user3 ;


            Book book ;
            Book book2 ;
            Book book3 ;




////         Devolvendo o livro com atraso para gerar multa
//           System.out.println("Devolvendo o livro com atraso...");
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.YEAR,2024);
//            calendar.set(Calendar.MONTH,9);
//            calendar.set(Calendar.DATE,24);
////
//           Date returnDate = calendar.getTime();
//


       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}

