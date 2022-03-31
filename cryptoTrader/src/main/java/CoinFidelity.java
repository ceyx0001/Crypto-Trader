import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import cryptoTrader.utils.operations.login.LoginUI;

/**
 * This class is the main driver prgoram for the system
 * 
 * @author Jun Shao
 * @since 2022-03-30
 */
public class CoinFidelity {
    private static Connection c = null;

    /**
     * Main method of the system
     * @param args Unused
     * @return void
     */
    public static void main(String[] args) {
        c = connect();
        LoginUI login = LoginUI.newLogin();
        newDB();
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /** 
     * Creates the database for storing broker and user information
     * @param Nothing
     * @return void
     */
    private static void newDB() {
        try {
            if (c != null) {
                DatabaseMetaData meta = c.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");

                String command = "CREATE TABLE IF NOT EXISTS Brokers ("
                        + "	id TEXT UNIQUE NOT NULL,"
                        + "	coins TEXT NOT NULL,"
                        + "	strat TEXT);";
                c.createStatement().execute(command);

                command = "CREATE TABLE IF NOT EXISTS Users ("
                        + "	id TEXT UNIQUE NOT NULL,"
                        + "	pass TEXT NOT NULL);";
                c.createStatement().execute(command);
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates the connection to the embedded database
     * @param Nothing
     * @return Connection The connection to the embedded database
     */
    private static Connection connect() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:cryptoTrader/src/main/java/cryptoTrader/db/local.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

}
