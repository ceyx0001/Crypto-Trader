package utils.db;

import java.sql.Connection;

/**
 * Abstract class which represents a database, used to implement the proxy design pattern
 */
public abstract class Database {
    /**
     * Abstract class which initializes a database
     */
    public abstract void init();

    /**
     * Abstract class which returns a Connection object
     * @return a Connection object
     */
    public abstract Connection getConnection();

    /**
     * Abstract class which is used to disconnect from the database
     */
    public abstract void disconnect();
}
