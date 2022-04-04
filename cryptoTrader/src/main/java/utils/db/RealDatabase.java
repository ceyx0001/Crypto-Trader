package utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class which is used to access the real embedded database, is a part of the
 * proxy design pattern as the actual database
 *
 * @author Jun Shao
 * @date 2022-04-04
 */
public class RealDatabase extends Database {
    private static RealDatabase dbinstance;
    private Connection connection;

    /**
     * Returns the instance of the embedded database
     *
     * @return Database the instance of the embedded database
     */
    protected static RealDatabase getdb() {
        if (dbinstance == null) {
            dbinstance = new RealDatabase();
        }
        return dbinstance;
    }

    /**
     * Constructor for the Security object
     */
    private RealDatabase() {
        connection = connect();
    }

    /**
     * Returns the connection to the embedded database
     *
     * @return Connection the connection to the embedded database
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * Creates the database for storing broker and user information
     */
    @Override
    public void init() {
        try {
            if (connection != null) {
                String command = "CREATE TABLE IF NOT EXISTS Brokers ("
                        + "	name TEXT NOT NULL,"
                        + "	strat TEXT NOT NULL,"
                        + "	target TEXT NOT NULL,"
                        + "	action TEXT NOT NULL,"
                        + "	amnt TEXT NOT NULL,"
                        + "	price TEXT NOT NULL,"
                        + "	date TEXT NOT NULL,"
                        + "	actionAmnt TEXT);";
                connection.createStatement().execute(command);

                command = "CREATE TABLE IF NOT EXISTS Users ("
                        + "	id TEXT UNIQUE NOT NULL,"
                        + "	pass TEXT NOT NULL);";
                connection.createStatement().execute(command);
            }
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }

    /**
     * Creates the connection to the embedded database
     *
     * @return Connection The connection to the embedded database
     */
    private Connection connect() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:src/main/java/utils/db/local.db");
        } catch (SQLException e) {
            System.out.println("Establishing connection failed: " + e.getMessage());
        }
        return c;
    }
}
