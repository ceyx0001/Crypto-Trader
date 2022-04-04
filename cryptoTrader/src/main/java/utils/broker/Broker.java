package utils.broker;

import java.util.HashMap;
import java.util.List;

import utils.broker.factoryObjects.Strategy;

/**
 * The class implementing the object associated with a broker
 * 
 * @author Anthony Tam
 * @date 2022-03-30
 */
public class Broker {
    private String name;
    private HashMap<String, Double> coins;
    private Strategy strategy;

    /**
     * The constructor for the Broker object
     * @param name the broker's name
     * @param coins the coins the broker is interested in
     * @param strategy the broker's strategy
     */
    public Broker(String name, List<String> coins, Strategy strategy) {
        this.name = name;
        this.coins = new HashMap<String, Double>();
        for (String s : coins) {
            this.coins.put(s, null);
        }
        this.strategy = strategy;
    }

    /**
     * Gets the name of the Broker object
     * @param Nothing
     * @return String the name of the Broker object
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the coins of the Broker object
     * @param Nothing
     * @return HashMap<String, Double> the coins of the Broker object
     */
    public HashMap<String, Double> getCoins() {
        return coins;
    }

    /**
     * Gets the strategy of the Broker object
     * @param Nothing
     * @return Strategy the strategy of the Broker object
     */
    public Strategy getStrat() {
        return strategy;
    }

    /**
     * Notifies the broker of the coin prices
     * @param coin the name of the coin
     * @return void
     */
    public void setPrice(String coin, double price) {
        coins.put(coin, price);
    }
}
