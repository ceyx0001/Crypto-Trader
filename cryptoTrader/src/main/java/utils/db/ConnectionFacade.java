package utils.db;

import java.sql.Connection;

public class ConnectionFacade {
    public Connection getConnection() {
        return Database.getdb().getConnection();
    }
}
