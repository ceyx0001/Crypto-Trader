package cryptoTrader.utils.operations.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database dbinstance;
    private static Connection connection;

    /**
     * Returns the instance of the embedded database
     * @param Nothing
     * @return Database the instance of the embedded database
     */
    public static Database getdb() {
        if (dbinstance == null) {
            dbinstance = new Database();
        }
        return dbinstance;
    }

    /**
     * Constructor for the Security object
     * 
     * @param Nothing
     * @return Nothing
     */
    private Database() {
        try {
            connection = connect();
            newDB();
            connection = DriverManager
                    .getConnection("jdbc:sqlite:cryptoTrader/src/main/java/cryptoTrader/db/local.db");
        } catch (SQLException e) {
            System.out.println("Establishing connection failed: " + e.getMessage());
        }
    }

    /**
     * Returns the connection to the embedded database
     * 
     * @param Nothing
     * @return Connection the connection to the embedded database
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Creates the database for storing broker and user information
     * 
     * @param Nothing
     * @return void
     */
    private void newDB() {
        try {
            if (connection != null) {
                String command = "CREATE TABLE IF NOT EXISTS Brokers ("
                        + "	id TEXT UNIQUE NOT NULL,"
                        + "	coins TEXT NOT NULL,"
                        + "	strat TEXT);";
                connection.createStatement().execute(command);

                command = "CREATE TABLE IF NOT EXISTS Users ("
                        + "	id TEXT UNIQUE NOT NULL,"
                        + "	pass TEXT NOT NULL);";
                connection.createStatement().execute(command);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates the connection to the embedded database
     * 
     * @param Nothing
     * @return Connection The connection to the embedded database
     */
    private Connection connect() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:cryptoTrader/src/main/java/cryptoTrader/db/local.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}
