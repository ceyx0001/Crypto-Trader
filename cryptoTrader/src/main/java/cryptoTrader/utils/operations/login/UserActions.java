package cryptoTrader.utils.operations.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements the data operations to
 * fetch and save user information to the embedded database
 * 
 * @author Jun Shao
 * @since 2022-03-30
 */
public class UserActions {
    private Connection connection;
    private String name;
    private String pass;

    /**
     * Constructor method to create an object instance of UserActions
     * @param name name of the user
     * @param pass password of the user
     * @return Nothing.
     */
    public UserActions(String name, String pass) {
        this.name = name;
        this.pass = pass;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:cryptoTrader/src/main/java/cryptoTrader/db/local.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines if the name and password of the user are valid
     * @param Nothing
     * @return boolean Determines if the authentication process was successful
     */
    public boolean authenticate() {
        try {
            String command = "SELECT id, pass FROM Users WHERE id = ?";
            PreparedStatement s  = connection.prepareStatement(command);
            s.setString(1, name);
            ResultSet rs  = s.executeQuery();
            String tempid = rs.getString("id");
            String tempPass = rs.getString("pass");
            connection.close();
            return (name.equals(tempid) && pass.equals(tempPass));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Saves the user information to the embedded database
     * @param Nothing
     * @return boolean Determines if the info was saved successfully
     */
    public boolean saveUser() {
        try {
            String command = "INSERT INTO Users(id, pass) VALUES(?,?)";
            PreparedStatement s = connection.prepareStatement(command);
            s.setString(1, name);
            s.setString(2, pass);
            s.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                System.out.println("User already registered.");
            } else {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
}
