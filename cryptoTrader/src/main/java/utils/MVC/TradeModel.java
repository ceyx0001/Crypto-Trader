package utils.MVC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import utils.broker.Broker;
import utils.broker.BrokerFactory;
import utils.db.Database;
import utils.db.DatabaseProxy;
import utils.tradingProcess.TradeManager;

/**
 * Class which represents the trading model, extends the Subject class in order to make use of the
 * observer design pattern.
 *
 * @author Jun Shao
 * @since 2022-03-30
 */
public class TradeModel extends Subject {
    private Connection connection;
    private HashMap<String, Broker> brokers;
    private HashSet<String> brokersInTable;
    BrokerFactory factory;
    private HashMap<String, HashMap<String, Integer>> data;
    private String[][] dataTable;
    private boolean missingInfo;

<<<<<<< HEAD
    protected TradeModel() {
=======
    /**
     * Constructor method for TradeModel
     */
    public TradeModel() {
>>>>>>> cea630b42ae8c4da094d0714665879381c695c2d
        Database proxy = new DatabaseProxy();
        factory = new BrokerFactory();
        proxy.init();
        connection = proxy.getConnection();
        brokers = new HashMap<String, Broker>();
        brokersInTable = new HashSet<String>();
        data = new HashMap<String, HashMap<String, Integer>>();
        missingInfo = false;
    }

<<<<<<< HEAD
    protected HashMap<String, Broker> getBrokers() {
        return brokers;
    }

    protected void logTrade() {
=======
    /**
     * Getter method that returns brokers
     * @return hashmap of brokers
     */
    public HashMap<String, Broker> getBrokers() {
        return brokers;
    }

    /**
     * Method which logs trade results to database
     */
    public void logTrade() {
>>>>>>> cea630b42ae8c4da094d0714665879381c695c2d
        try {
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

<<<<<<< HEAD
    protected void closeConnection() {
        try {
            System.out.println("Disconnected.");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Disconnect failed: " + e.getMessage());
        }
    }

    protected void removeBroker(String name) {
=======
    /**
     * Method which removes a broker from the database
     * @param name is a String with a broker's name
     */
    public void removeBroker(String name) {
>>>>>>> cea630b42ae8c4da094d0714665879381c695c2d
        brokers.remove(name);
    }

    /**
     * Method which creates a new broker with given parameters and the BrokerFactory,
     * which is used to implement a factory design pattern
     *
     * @param name is the name of the broker
     * @param coins is a String containing the coins a broker is interested in
     * @param strat is the broker's trading strategy
     * @return the new broker that has been created
     */
    protected Broker newBroker(String name, String coins, String strat) {
        return factory.getBroker(name, coins, strat);
    }

    /**
     * Method which tallies the number of times a trading broker has made a transaction
     * @param broker is a broker
     * @param strat is the strategy used by the broker
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
     * Method which sets data table and whether or not there is missing info
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

    protected HashMap<String, HashMap<String, Integer>> getDataMap() {
        return data;
    }

    protected String[][] getDataTable() {
        return dataTable;
    }

    protected void setDataTable() {
        dataTable = TradeManager.getInstance().trade(brokers).getTable();
    }

    protected boolean hasDupe(String broker) {
        return brokersInTable.contains(broker);
    }

    protected void printInTable() {
        System.out.println(Arrays.toString(brokersInTable.toArray()));
    }

    protected void addBrokerInTable(String name) {
        brokersInTable.add(name);
    }

    protected void removeBrokerInTable(String name) {
        brokersInTable.remove(name);
    }

    protected boolean isMissingInfo() {
        return missingInfo;
    }
}
