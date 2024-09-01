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

           // Criando e inserindo um novo usuário

           User user = new User();
           user = userDAO.findUserById(1239);
//           user.setName("Ricardo Cezar");
//           user.setEmail("rick.cezar@example.com");
//           user.setPassword("strongpassword13423");
//           user.setRole(Role.STUDENT);
//
//           System.out.println("Registrando novo usuário...");
//           userService.registerUser(user);
//           System.out.println("Usuário registrado com ID: " + user.getId());

           // Criando e inserindo um novo livro
           Book book = new Book();
           book = bookDAO.findBookById(2);
//           book.setTitle("Poder do Hábito");
//           book.setAuthor("Charles Duhigg");
//           book.setISBN(978140006);
//           book.setGenre("self-help");
//           book.setAvailability(true);
//
//           System.out.println("Adicionando novo livro...");
//           bookDAO.add(book);
//           System.out.println("Livro adicionado com ID: " + book.getId());

           // Criando um novo empréstimo
           System.out.println("Realizando um empréstimo de livro...");
           Borrow borrow = borrowService.addBookBorrow(book.getId(), user.getId(), 14);
           System.out.println("Empréstimo realizado com ID: " + borrow.getId());

           // Devolvendo o livro com atraso para gerar multa
           System.out.println("Devolvendo o livro com atraso...");
           Date returnDate = new Date(); // Data de hoje como data de devolução
           borrowService.returnBook(borrow.getId(), returnDate);
           System.out.println("Livro devolvido.");

           // Buscando multas geradas para o usuário
           List<Fine> fines = fineDAO.getAllFinesForUser(user.getId());
           if (!fines.isEmpty()) {
               Fine fine = fines.get(0);
               System.out.println("Multa aplicada ao usuário: R$" + fine.getAmount() + " (ID da multa: " + fine.getId() + ")");
           } else {
               System.out.println("Nenhuma multa encontrada para o usuário.");
           }

           // Autenticação do usuário
           boolean isAuthenticated = userService.authenticateUser(user.getEmail(), user.getPassword());
           System.out.println("Autenticação: " + (isAuthenticated ? "Bem-sucedida" : "Falhou"));

       } catch (SQLException e) {
           e.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}

