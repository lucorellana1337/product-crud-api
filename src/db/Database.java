package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:sqlite:db/products.db";

    public static Connection connect() throws Exception {
        return DriverManager.getConnection(URL);
    }

    public static void init() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS productos (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "nombre TEXT," +
                         "categoria TEXT," +
                         "precio REAL," +
                         "stock INTEGER)";
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
