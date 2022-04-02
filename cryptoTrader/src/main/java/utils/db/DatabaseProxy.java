package utils.db;

import java.sql.Connection;

public class DatabaseProxy implements DatabaseInterface {
    private static Database db;

    @Override
    public Connection getConnection() {
        if (db != null) {
            return db.getConnection();
        }
        return null;
    }

    @Override
    public void init() {
        db = Database.getdb();
        db.init();
    }
}
