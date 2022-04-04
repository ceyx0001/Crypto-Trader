package utils.MVC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private HashSet<String> brokersInTable;
    BrokerFactory factory;
    private HashMap<String, HashMap<String, Integer>> data;
    private String[][] dataTable;
    private boolean missingInfo;

    /**
     * Constructor for TradeModel class which initializes its fields
     * 
     * @param Nothing
     * @return Nothing
     */
    public TradeModel() {
        Database proxy = new DatabaseProxy();
        factory = new BrokerFactory();
        proxy.init();
        connection = proxy.getConnection();
        brokers = new HashMap<String, Broker>();
        brokersInTable = new HashSet<String>();
        data = new HashMap<String, HashMap<String, Integer>>();
        missingInfo = false;
    }

    public HashMap<String, Broker> getBrokers() {
        return brokers;
    }

    public void logTrade() {
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

    public void removeBroker(String name) {
        brokers.remove(name);
    }

    protected Broker newBroker(String name, String coins, String strat) {
        return factory.getBroker(name, coins, strat);
    }

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
