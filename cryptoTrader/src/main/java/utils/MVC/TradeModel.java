package utils.MVC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import utils.broker.Broker;
import utils.broker.BrokerFactory;
import utils.db.Database;
import utils.db.DatabaseProxy;
import utils.tradingProcess.TradeManager;

/**
 * Class which represents the trading model, extends the Subject class in order
 * to make use of the
 * observer design pattern.
 *
 * @author Ernest Li, Simone Sequeira
 * @date 2022-03-30
 */
public class TradeModel extends Subject {
    private Connection connection;
    private HashMap<String, Broker> brokers; // the map mapping broker names to its objects
    private HashSet<String> brokersInTable; // the set containing brokers the user added
    BrokerFactory factory;
    private HashMap<String, HashMap<String, Integer>> data; // the map mapping broker names to their # of actions done
    private String[][] dataTable; // the array used to convert data into a UI default table model
    private boolean missingInfo;

    protected TradeModel() {
        Database proxy = new DatabaseProxy();
        factory = new BrokerFactory();
        proxy.init();
        connection = proxy.getConnection();
        brokers = new HashMap<String, Broker>();
        brokersInTable = new HashSet<String>();
        data = new HashMap<String, HashMap<String, Integer>>();
        missingInfo = false;
    }

    /**
     * Method which returns the broker objects inside the system
     * 
     * @return HashMap<String, Broker> the map of the system's current brokers
     */
    protected HashMap<String, Broker> getBrokers() {
        return brokers;
    }

    /**
     * Method which saves all the trading activities into the local database
     */
    protected void logTrade() {
        try {
            // sqlite save
            String insert = "INSERT OR REPLACE INTO Brokers(name, strat, target, action, amnt, price, date, actionAmnt) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement s = connection.prepareStatement(insert);

            for (String name : brokers.keySet()) {
                String strat = brokers.get(name).getStrat().getType();
                String target = brokers.get(name).getStrat().getTarget();
                String action = brokers.get(name).getStrat().getAction();
                String amnt = String.valueOf(brokers.get(name).getStrat().getAmntBought());
                String price = brokers.get(name).getPrice();
                String date = getDate();
                String actionAmnt = Integer.toString(data.get(name).get(strat));

                s.setString(1, name);
                s.setString(2, strat);
                s.setString(3, target);
                s.setString(4, action);
                s.setString(5, amnt);
                s.setString(6, price);
                s.setString(7, date);
                s.setString(8, actionAmnt);
                s.execute();
            }
        } catch (SQLException e) {
            System.out.println("saveData: " + e.getMessage());
        }
    }

    /**
     * Gets today's calendar day
     * 
     * @return String today's date
     */
    private String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateVar = new Date();
        return formatter.format(dateVar);
    }

    /**
     * Closes the connection to the embedded database
     */
    protected void closeConnection() {
        try {
            System.out.println("Disconnected.");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Disconnect failed: " + e.getMessage());
        }
    }

    /**
     * Removes a broker object from the system
     * 
     * @param name the name of the broker
     */
    protected void removeBroker(String name) {
        brokers.remove(name);
    }

    /**
     * Adds a broker object into the system
     * 
     * @param name  the broker's name
     * @param coins the broker's coin list
     * @param strat the broker's strat
     * @return Broker the associated broker object
     */
    protected Broker newBroker(String name, String coins, String strat) {
        return factory.getBroker(name, coins, strat);
    }

    /**
     * Method which tallies the number of times a trading broker has made a
     * transaction
     * 
     * @param broker broker's name
     * @param strat  is the strategy used by the broker
     */
    private void tally(String broker, String strat) {
        HashMap<String, Integer> brokerStrats;
        if (data.get(broker) == null) {
            brokerStrats = new HashMap<String, Integer>();
            brokerStrats.put(strat, 1);
            data.put(broker, brokerStrats);
        } else {
            brokerStrats = data.get(broker);
            brokerStrats.put(strat, brokerStrats.get(strat) + 1);
            data.put(broker, brokerStrats);
        }
    }

    /**
     * Method which sets UI data table and whether or not there is missing info
     */
    protected void setDataMap() {
        missingInfo = false;
        for (int row = 0; row < dataTable.length; row++) {
            tally(dataTable[row][0], dataTable[row][1]);
            if (dataTable[row][5].equals("Null")) {
                missingInfo = true;
            }
        }
    }

    /**
     * Gets the number of actions the current brokers have done
     * 
     * @return HashMap<String, HashMap<String, Integer>> the map of broker actions
     */
    protected HashMap<String, HashMap<String, Integer>> getDataMap() {
        return data;
    }

    /**
     * Gets the UI data table
     * 
     * @return String[][] the data table containing trading activity
     */
    protected String[][] getDataTable() {
        return dataTable;
    }

    /**
     * Gets the data into the data table
     */
    protected void setDataTable() {
        dataTable = TradeManager.getInstance().trade(brokers).getTable();
    }

    /**
     * determines if a broker is already added into the system
     * 
     * @param broker the broker's name to check
     * @return boolean if the broker already exists
     */
    protected boolean hasDupe(String broker) {
        return brokersInTable.contains(broker);
    }

    /**
     * Adds a broker to the UI data table
     * 
     * @param name the broker's name
     */
    protected void addBrokerInTable(String name) {
        brokersInTable.add(name);
    }

    /**
     * Adds a broker to the UI data table
     * 
     * @param name the broker's name
     */
    protected void removeBrokerInTable(String name) {
        brokersInTable.remove(name);
    }

    /**
     * Determines if a broker did not have a complete coin list
     * @return boolean if the broker has the required coin list
     */
    protected boolean isMissingInfo() {
        return missingInfo;
    }
}
