package com.bibliotrack.dao;

import com.bibliotrack.entities.Book;

import java.sql.SQLException;
import java.util.List;

public class BookDAO extends BaseDAO<Book> {
    @Override
    protected String getTableName() {
        return "book";
    }
    public Book addBook(Book book) throws SQLException {
        return add(book);
    }
    public void removeBook(int id) throws SQLException {
        remove("id",id);
    }
    public Book editBook(Book book) throws SQLException {
        int originalId = book.getBookId();
        return edit(book,"id",originalId);
    }
    public Book findBookById(int id) throws SQLException {
        List<Book> books = find("id",id,Book.class);
        return books.isEmpty() ? null : books.get(0);
    }
    public List<Book> searchByName(String name) throws SQLException {
        List<Book> books = find("name",name, Book.class);
        return books.isEmpty() ? null : books;
    }
    public List <Book> searchByAuthor(String authorName) throws SQLException {
        List <Book> books = find("author",authorName, Book.class);
        return books.isEmpty() ? null : books;
    }
    public List <Book> searchByGenre(String genre) throws SQLException {
        List <Book> books = find("genre",genre, Book.class);
        return books.isEmpty() ? null : books;
    }

}
