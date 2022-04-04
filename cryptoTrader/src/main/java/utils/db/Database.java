package utils.db;

import java.sql.Connection;

public abstract class Database {
    public abstract void init();
    public abstract Connection getConnection();
    public abstract void disconnect();
}
