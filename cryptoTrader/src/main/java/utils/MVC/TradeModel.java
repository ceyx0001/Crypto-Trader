package utils.MVC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import utils.broker.Broker;
import utils.broker.BrokerFactory;
import utils.db.DatabaseProxy;
import utils.tradingProcess.TradeProcess;

/**
 * Class which
 */
public class TradeModel extends Subject {
    private Connection connection;
    private HashMap<String, Broker> brokers;
    private List<String> neededCoins;

    public TradeModel() {
        connection = new DatabaseProxy().getConnection();
        brokers = new HashMap<String, Broker>();
        neededCoins = new ArrayList<String>();
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
                brokers.get(name).getStrat().getName();
                i++;
            }

            return new DefaultTableModel(data, headers);
        } catch (SQLException e) {
            System.out.println("getBrokersTable: " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

    public HashMap<String, Broker> getBrokers() {
        return brokers;
    }

    public List<String> getNeededCoins() {
        return neededCoins;
    }

    public void saveBrokers() {
        try {
            String insert = "INSERT OR REPLACE INTO Brokers(id, coins, strat) VALUES(?,?,?)";
            PreparedStatement s = connection.prepareStatement(insert);

            for (String key : brokers.keySet()) {
                s.setString(1, key);
                s.setString(2, brokers.get(key).getCoins().toString().replaceAll("\\[|\\]", "").toUpperCase());
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

    public String[][] getResults() {
        return new TradeProcess().trade(brokers, neededCoins).getTable();
    }
}
