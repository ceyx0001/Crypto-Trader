package cryptoTrader.utils.trade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class TradeModel {
    private Connection connection;
    
    public TradeModel() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:cryptoTrader/src/main/java/cryptoTrader/db/local.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public DefaultTableModel getBrokers() {
        try {
            String command = "SELECT * FROM Brokers";
            String rowsCommand = "SELECT COUNT(1) FROM Brokers";
            PreparedStatement getBrokers = connection.prepareStatement(command);
            PreparedStatement getRows = connection.prepareStatement(rowsCommand);
            ResultSet rs = getBrokers.executeQuery();
            ResultSet rowsrs = getRows.executeQuery();
            rowsrs.next();
            int rows = rowsrs.getInt(1);

            String headers[] = { "Trading Client", "Coin List", "Strategy Name" };
            String data[][] = new String[rows][3];

            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("id");
                data[i][1] = rs.getString("coins");
                data[i][2] = rs.getString("strat");
                i++;
            }

            return new DefaultTableModel(data, headers);
        } catch (SQLException e) {
            System.out.println("getBrokers: " + e.getMessage());
        }
        return null;
    }

    public boolean saveBrokers(HashMap<String, List<String>> brokers) {
        try {
            String command = "INSERT INTO Brokers(id, coins, strat) VALUES(?,?,?)";
            PreparedStatement s = connection.prepareStatement(command);

            for (String key : brokers.keySet()) {
                s.setString(1, key);
                s.setString(2, brokers.get(key).get(0));
                s.setString(3, brokers.get(key).get(1));
                s.execute();
            }
            
            return true;
        } catch (SQLException e) {
            System.out.println("saveBrokers: " + e.getMessage());
        }
        return false;
    }
}
