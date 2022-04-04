package utils.broker;

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
    private List<String> coins;
    private Strategy strategy;

    /**
     * The constructor for the Broker object
     * @param name the broker's name
     * @param coins the coins the broker is interested in
     * @param strategy the broker's strategy
     */
    public Broker(String name, List<String> coins, Strategy strategy) {
        this.name = name;
        this.coins = coins;
        this.strategy = strategy;
    }

    /**
     * Gets the name of the Broker object
     * @return String the name of the Broker object
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the coins of the Broker object
     * @return List<String> the coins of the Broker object
     */
    public List<String> getCoins() {
        return coins;
    }

    /**
     * Gets the strategy of the Broker object
     * @return Strategy the strategy of the Broker object
     */
    public Strategy getStrat() {
        return strategy;
    }
}
