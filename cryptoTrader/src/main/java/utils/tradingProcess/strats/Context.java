package utils.tradingProcess.strats;

import java.util.ArrayList;
import java.util.HashMap;

import utils.broker.Broker;
import utils.tradingProcess.TradeResult;

/**
 * Class which acts as context for transactions to modify their behaviour on
 * run-time
 *
 * @author Jun Shao
 * @since 2022-04-03
 */
public class Context {
    private Transaction transaction;
    private TradeResult tr;
    private Broker b;
    private HashMap<String, Double> prices;
    private ArrayList<String> availableCoins;

    /**
     * Constructor method for the Context class which assigns variables values given by parameters.
     *
     * @param transaction is a transaction object
     * @param b is a broker object which represents a trading broker
     * @param prices is a hashmap which stores prices for coins
     * @param tr is an object which represents the results of a trade
     */
    public Context(Transaction transaction, Broker b, HashMap<String, Double> prices, TradeResult tr, ArrayList<String> availableCoins) {
        this.transaction = transaction;
        this.b = b;
        this.prices = prices;
        this.tr = tr;
        this.availableCoins = availableCoins;
    }

    /**
     * Getter method which returns broker object
     *
     * @return broker object which represents trade broker
     */
    public Broker getBroker() {
        return b;
    }

    /**
     * Getter method which returns hashmap of coin prices
     *
     * @return hashmap of coin prices
     */
    public HashMap<String, Double> getPrices() {
        return prices;
    }

    /**
     * Getter method which returns trade results
     *
     * @return trade results
     */
    public TradeResult getTradeResult() {
        return tr;
    }

    /**
     * Getter method which returns the available coins of the system
     *
     * @return ArrayList<String> the available coins of the system
     */
    public ArrayList<String> getAvailableCoins() {
        return availableCoins;
    }

    /**
     * Method which executes trade with given context
     */
    public void execute() {
        transaction.trade(this);
    }
}
