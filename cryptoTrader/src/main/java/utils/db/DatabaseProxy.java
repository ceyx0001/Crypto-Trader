package utils.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class which is used to represent a database proxy, which is a placeholder for a database object in the
 * proxy design pattern and serves as a substitute for the RealDatabase object
 *
 * @author Jun Shao
 * @since 2022-04-03
 */
public class DatabaseProxy extends Database {
    private RealDatabase db;

    /**
     * Method which initializes the database and the real embedded database
     */
    @Override
    public void init() {
        db = RealDatabase.getdb();
        db.init();
    }

    /**
     * Getter method which returns a Connection object representing the connection to the database
     * @return Connection object representing the connection to the database
     */
    @Override
    public Connection getConnection() {
        if (db != null) {
            return db.getConnection();
        }
        return null;
    }

    /**
     * Method which disconnects from the database
     */
    @Override
    public void disconnect() {
        try {
            db.getConnection().close();
        } catch (SQLException e) {
            System.out.println("Disconnect failed: " + e.getMessage());
        }
    }
}
