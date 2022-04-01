package cryptoTrader.utils.operations.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cryptoTrader.utils.operations.main.Database;

/**
 * This is the class that handles the user login of the system
 * 
 * @author Jun Shao
 * @since 2022-03-30
 */
public class UserOperation {
    private String name;
    private String pass;
    private Connection connection;

    /**
     * Creates a new instance of the register strategy
     * 
     * @param name is the name of the user
     * @param pass is the password of the user
     */
    public UserOperation(String name, String pass) {
        this.name = name;
        this.pass = pass;
        connection = Database.getdb().getConnection();
    }

    /**
     * Saves the user information to the embedded database
     * 
     * @param Nothing
     * @return boolean Determines if the info was saved successfully
     */
    protected boolean saveUser() {
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

    /**
     * Determines if the name and password of the user are valid
     * 
     * @param Nothing
     * @return boolean Determines if the authentication process was successful
     */
    protected boolean authenticate() {
        try {
            String command = "SELECT id, pass FROM Users WHERE id = ?";
            PreparedStatement s = connection.prepareStatement(command);
            s.setString(1, name);
            ResultSet rs = s.executeQuery();
            String tempid = rs.getString("id");
            String tempPass = rs.getString("pass");
            connection.close();
            return (name.equals(tempid) && pass.equals(tempPass));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
