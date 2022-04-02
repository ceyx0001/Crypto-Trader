package utils.db;

import java.sql.Connection;

public interface DatabaseInterface {
    public void init();
    public Connection getConnection();
}
