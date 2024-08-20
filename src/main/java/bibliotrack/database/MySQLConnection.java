package bibliotrack.database;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/?user=root/bibliotrack";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public DSLContext connect() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return DSL.using(connection, SQLDialect.MYSQL);
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void fechar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
