package cryptoTrader.utils.trade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class TradeModel {
    private Connection connection;
    private HashMap<String, List<String>> brokers;
    
    public TradeModel() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:cryptoTrader/src/main/java/cryptoTrader/db/local.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        brokers = new HashMap<String, List<String>>();
    }

    public DefaultTableModel getBrokersTable() {
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
                List<String> temp = new ArrayList<String>();
                temp.add(rs.getString("coins"));
                temp.add(rs.getString("strat"));
                brokers.put(rs.getString("id"), temp);
                i++;
            }

            return new DefaultTableModel(data, headers);
        } catch (SQLException e) {
            System.out.println("getBrokers: " + e.getMessage());
        }
        return null;
    }

    public HashMap<String, List<String>> getBrokers() {
        return brokers;
    }

    public void saveBrokers() {
        try {
            String insert = "INSERT INTO Brokers(id, coins, strat) VALUES(?,?,?)";
            PreparedStatement s = connection.prepareStatement(insert);

            for (String key : brokers.keySet()) {
                s.setString(1, key);
                s.setString(2, brokers.get(key).get(0).replaceAll("\\[\\]", ""));
                s.setString(3, brokers.get(key).get(1));
                s.execute();
            }
            
        } catch (SQLException e) {
            System.out.println("saveBrokers: " + e.getMessage());
        }
    }

    public boolean removeBroker() {
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
