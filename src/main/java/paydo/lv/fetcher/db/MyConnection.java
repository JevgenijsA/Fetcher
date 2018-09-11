package paydo.lv.fetcher.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    static Connection conn;

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/paydo/lv/fetcher/assets/fetcher.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
