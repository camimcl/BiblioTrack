CREATE DATABASE BiblioTrack;
USE BiblioTrack;

CREATE TABLE User(
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(100) NOT NULL,
                     email VARCHAR(100) UNIQUE NOT NULL,
                     password VARCHAR(100) NOT NULL,
                     role VARCHAR(20) NOT NULL
);

CREATE TABLE Book(
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     title VARCHAR(255) NOT NULL,
                     author VARCHAR(255) NOT NULL,
                     ISBN VARCHAR(13) UNIQUE NOT NULL,
                     genre VARCHAR(100),
                     availability BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE Borrow (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        userID INT NOT NULL,
                        bookID INT NOT NULL,
                        borrowDate DATE NOT NULL,
                        returnDate DATE,
                        dueDate DATE NOT NULL,
                        returned BOOLEAN NOT NULL DEFAULT FALSE,
                        fine DECIMAL(10, 2) DEFAULT 0.00,
                        FOREIGN KEY (userID) REFERENCES User(id),
                        FOREIGN KEY (bookID) REFERENCES Book(id)
);

CREATE TABLE Reservation (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             userID INT NOT NULL,
                             bookID INT NOT NULL,
                             reservationDate DATE NOT NULL,
                             FOREIGN KEY (userID) REFERENCES User(id),
                             FOREIGN KEY (bookID) REFERENCES Book(id)
);

CREATE TABLE Fine (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      userID INT NOT NULL,
                      amount DECIMAL(10, 2) NOT NULL,
                      paid BOOLEAN NOT NULL DEFAULT FALSE,
                      FOREIGN KEY (userID) REFERENCES User(id)
);

CREATE TABLE logActivity (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             userID INT NOT NULL,
                             description VARCHAR(255) NOT NULL,
                             logDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (userID) REFERENCES User(id)
);
