package utils.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseProxy extends Database {
    private RealDatabase db;

    @Override
    public void init() {
        db = RealDatabase.getdb();
        db.init();
    }

    @Override
    public Connection getConnection() {
        if (db != null) {
            return db.getConnection();
        }
        return null;
    }

    @Override
    public void disconnect() {
        try {
            db.getConnection().close();
        } catch (SQLException e) {
            System.out.println("Disconnect failed: " + e.getMessage());
        }
    }
}
