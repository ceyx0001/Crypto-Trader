package cryptoTrader.utils.trade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import cryptoTrader.utils.broker.Broker;
import cryptoTrader.utils.broker.BrokerFactory;

public class TradeModel {
    private Connection connection;
    private HashMap<String, Broker> brokers;
    
    public TradeModel() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:cryptoTrader/src/main/java/cryptoTrader/db/local.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        brokers = new HashMap<String, Broker>();
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
                String name = rs.getString("id");
                String strat = rs.getString("strat");
                data[i][0] = name;
                data[i][1] = rs.getString("coins");
                data[i][2] = strat;
                brokers.put(name, newBroker(name, rs.getString("coins"), strat));
                i++;
            }

            return new DefaultTableModel(data, headers);
        } catch (SQLException e) {
            System.out.println("getBrokers: " + e.getMessage());
        }
        return null;
    }

    public HashMap<String, Broker> getBrokers() {
        return brokers;
    }

    public void saveBrokers() {
        try {
            String insert = "INSERT OR IGNORE INTO Brokers(id, coins, strat) VALUES(?,?,?)";
            PreparedStatement s = connection.prepareStatement(insert);

            for (String key : brokers.keySet()) {
                s.setString(1, key);
                s.setString(2, brokers.get(key).getCoins().toString().replaceAll("\\[|\\]", ""));
                s.setString(3, brokers.get(key).getStrat().getName());
                s.execute();
            }
        } catch (SQLException e) {
            System.out.println("saveBrokers: " + e.getMessage());
        }
    }

    public void removeBroker(String name) {
        try {
            String insert = "DELETE FROM Brokers WHERE id = ?";
            PreparedStatement s = connection.prepareStatement(insert);
            s.setString(1, name);
            s.execute();
            brokers.remove(name); 
        } catch (SQLException e) {
            System.out.println("saveBrokers: " + e.getMessage());
        }
    }

    public Broker newBroker(String name, String coins, String strat) {
        BrokerFactory factory = new BrokerFactory();
        return factory.getBroker(name, coins, strat);
    }
}
