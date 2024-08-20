package com.bibliotrack;

import bibliotrack.database.DB;
import bibliotrack.database.MySQLConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = MySQLConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}