package utils.MVC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import utils.broker.Broker;
import utils.broker.BrokerFactory;
import utils.db.Database;
import utils.db.DatabaseProxy;
import utils.tradingProcess.TradeManager;

/**
 * The model component of the system that implements
 * the data operations required to save/retrieve brokers to/from the
 * embedded database and initiates the trading process
 * 
 * @author Jun Shao
 * @since 2022-03-30
 */
public class TradeModel extends Subject {
    private Connection connection;
    private HashMap<String, Broker> brokers;
    private ArrayList<String> neededCoins;

    /**
     * Constructor for TradeModel class which initializes its fields
     * 
     * @param Nothing
     * @return Nothing
     */
    public TradeModel() {
        Database proxy = new DatabaseProxy();
        proxy.init();
        connection = proxy.getConnection();
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
            String headers[] = { "Broker name", "Coin List", "Strategy Name" };
            String data[][] = new String[rows][3];

            int i = 0;
            while (rs.next()) {
                String name = rs.getString("id");
                String strat = rs.getString("strat");
                data[i][0] = name;
                data[i][1] = rs.getString("coins");
                data[i][2] = strat;
                brokers.put(name, newBroker(name, rs.getString("coins"), strat));
                brokers.get(name).getStrat().getType();
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

    public ArrayList<String> getNeededCoins() {
        return neededCoins;
    }

    public void saveBrokers() {
        try {
            String insert = "INSERT OR REPLACE INTO Brokers(id, coins, strat) VALUES(?,?,?)";
            PreparedStatement s = connection.prepareStatement(insert);

            for (String key : brokers.keySet()) {
                s.setString(1, key);
                Broker b = brokers.get(key);
                String coins = b.getCoins().toString().replaceAll("\\{|\\}|=|\\s|\\W", "").toUpperCase();
                coins = coins.replace("NULL", "");
                s.setString(2, coins);
                s.setString(3, b.getStrat().getType());
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
            System.out.println("removeBrokers: " + e.getMessage());
        }
    }

    public Broker newBroker(String name, String coins, String strat) {
        BrokerFactory factory = new BrokerFactory();
        return factory.getBroker(name, coins, strat);
    }

    public String[][] getResults() {
        return TradeManager.getInstance(neededCoins).trade(brokers).getTable();
    }
}
