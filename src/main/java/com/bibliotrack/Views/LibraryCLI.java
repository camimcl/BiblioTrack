package com.bibliotrack.Views;

import com.bibliotrack.dao.BookDAO;
import com.bibliotrack.dao.ReservationDAO;
import com.bibliotrack.dao.UserDAO;
import com.bibliotrack.enums.Role;
import com.bibliotrack.exceptions.BookNotAvailableException;
import com.bibliotrack.exceptions.BookNotFoundException;
import com.bibliotrack.services.*;
import com.bibliotrack.entities.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LibraryCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = UserService.getInstance();
    private static final BookService bookService = new BookService();
    private static final BorrowService borrowService = new BorrowService();
    private static final ReservationService reservationService = new ReservationService();
    private static final FineService fineService = new FineService();
    private static final UserDAO userDAO = new UserDAO();
    private static final BookDAO bookDAO = new BookDAO();
    private static final ReservationDAO  reservationDAO = new ReservationDAO();

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            try {
                switch (choice) {
                    case 1 -> addUser();
                    case 2 -> addBook();
                    case 3 -> borrowBook();
                    case 4 -> returnBook();
                    case 5 -> makeReservation();
                    case 6 -> applyFine();
                    case 7 -> editUser();
                    case 8 -> editBook();
                    case 9 -> exitProgram();
                    default -> System.out.println("Escolha inválida. Tente novamente.");
                }
            } catch (SQLException | BookNotFoundException | BookNotAvailableException e) {
                e.printStackTrace();
                System.out.println("Erro ao realizar operação: " + e.getMessage());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("=== Sistema de Gerenciamento de Biblioteca ===");
        System.out.println("1. Adicionar Usuário");
        System.out.println("2. Adicionar Livro");
        System.out.println("3. Realizar Empréstimo");
        System.out.println("4. Realizar Devolução");
        System.out.println("5. Fazer Reserva");
        System.out.println("6. Aplicar Multa");
        System.out.println("7. Editar Usuário");
        System.out.println("8. Editar Livro");
        System.out.println("9. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void addUser() throws SQLException {
        System.out.println("=== Adicionar Novo Usuário ===");
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();
        System.out.print("Função (role): ");
        String role = scanner.nextLine();

        User user = new User(name, email, password, Role.valueOf(role));
        userService.registerUser(user);
        System.out.println("Usuário adicionado com sucesso!");
    }

    private static void addBook() throws SQLException {
        System.out.println("=== Adicionar Novo Livro ===");
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Autor: ");
        String author = scanner.nextLine();
        System.out.print("Gênero: ");
        String genre = scanner.nextLine();
        System.out.print("ISBN: ");
        long isbn = scanner.nextLong();

        Book book = new Book(title,isbn,author,genre,true);
        bookDAO.addBook(book);
        System.out.println("Livro adicionado com sucesso!");
    }

    private static void borrowBook() throws SQLException, BookNotFoundException, BookNotAvailableException {
        System.out.println("=== Realizar Empréstimo ===");
        System.out.print("ID do Usuário: ");
        int userId = scanner.nextInt();
        System.out.print("ID do Livro: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.println("Qunatidade de dias emprestado :");
        int borrowPeriodDays = scanner.nextInt();
        borrowService.addBookBorrow(bookId,userId,borrowPeriodDays);
        System.out.println("Empréstimo realizado com sucesso!");
    }

    private static void returnBook() throws SQLException, ParseException {
        System.out.println("=== Realizar Devolução ===");
        System.out.print("ID do Empréstimo: ");
        int borrowId = scanner.nextInt();
        System.out.print("Data de Devolução (yyyy/mm/dd): ");
        String returnDate = scanner.next();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        borrowService.returnBook(borrowId, sdf.parse(returnDate));
        System.out.println("Devolução realizada com sucesso!");
    }

    private static void makeReservation() throws SQLException {
        System.out.println("=== Fazer Reserva ===");
        System.out.print("ID do Usuário: ");
        int userId = scanner.nextInt();
        System.out.print("ID do Livro: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        Reservation reservation = new Reservation(userId,bookId, new Date());
        reservationDAO.addReservation(reservation);
        System.out.println("Reserva realizada com sucesso!");
    }

    private static void applyFine() throws SQLException {
        System.out.println("=== Aplicar Multa ===");
        System.out.print("ID do Usuário: ");
        int userId = scanner.nextInt();
        System.out.print("Valor da Multa: ");
        double fineAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        fineService.applyFine(userDAO.findUserById(userId), fineAmount);
        System.out.println("Multa aplicada com sucesso!");
    }

    private static void editUser() throws SQLException {
        System.out.println("=== Editar Usuário ===");
        System.out.print("ID do Usuário: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Usando o UserService para buscar o usuário
        User user = userDAO.findUserById(userId);
        if (user == null) {
            System.out.println("Usuário não encontrado!");
            return;
        }

        System.out.print("Novo Nome (atual: " + user.getName() + "): ");
        user.setName(scanner.nextLine());
        System.out.print("Novo Email (atual: " + user.getEmail() + "): ");
        user.setEmail(scanner.nextLine());
        System.out.print("Nova Senha (atual: [PROTEGIDO]): ");
        user.setPassword(scanner.nextLine());
        System.out.print("Nova Função (atual: " + user.getRole() + "): ");
        String roleStr = scanner.nextLine();
        Role role = Role.valueOf(roleStr);
        user.setRole(role);

        // Agora chama o método de edição do UserService
        userService.editUser(user);
        System.out.println("Usuário editado com sucesso!");
    }


    private static void editBook() throws SQLException {
        System.out.println("=== Editar Livro ===");
        System.out.print("ID do Livro: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Book book = bookDAO.findBookById(bookId);
        if (book == null) {
            System.out.println("Livro não encontrado!");
            return;
        }

        System.out.print("Novo Título (atual: " + book.getTitle() + "): ");
        book.setTitle(scanner.nextLine());
        System.out.print("Novo Autor (atual: " + book.getAuthor() + "): ");
        book.setAuthor(scanner.nextLine());
        System.out.print("Novo Gênero (atual: " + book.getGenre() + "): ");
        book.setGenre(scanner.nextLine());
        System.out.print("Nova disponibilidade  (atual: " + book.isAvailable() + "): ");
        book.setAvailability(scanner.nextBoolean());

        bookDAO.editBook(book);
        System.out.println("Livro editado com sucesso!");
    }

    private static void exitProgram() {
        System.out.println("Encerrando o programa...");
        System.exit(0);
    }
}
