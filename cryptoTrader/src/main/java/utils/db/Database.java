package utils.db;

import java.sql.Connection;

/**
 * Abstract class which represents a database, used to implement the proxy design pattern
 *
 * @author Jun Shao
 * @since 2022-04-02
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
}
