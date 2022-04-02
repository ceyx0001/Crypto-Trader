package utils.db;

import java.sql.Connection;
import java.util.Queue;

public interface DatabaseInterface {
    public void init();
    public Connection getConnection();
}
