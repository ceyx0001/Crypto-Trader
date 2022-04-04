package utils.broker;

import java.util.HashMap;
import java.util.List;

import utils.broker.factoryObjects.Strategy;

/**
 * The class implementing the object associated with a broker
 * 
 * @author Jun Shao, Anthony Tam
 * @date 2022-03-30
 */
public class Broker {
    private String name;
    private HashMap<String, Double> interestPrices;
    private String targetPrice;
    private Strategy strategy;

    /**
     * The constructor for the Broker object
     * @param name the broker's name
     * @param coins the coins the broker is interested in
     * @param strategy the broker's strategy
     */
    public Broker(String name, List<String> coins, Strategy strategy) {
        this.name = name;
        interestPrices = new HashMap<String, Double>();
        for (String s : coins) {
            interestPrices.put(s, null);
        }
        this.strategy = strategy;
        targetPrice = null;
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
     * @return HashMap<String, Double> the coins of the Broker object
     */
    public HashMap<String, Double> getInterest() {
        return interestPrices;
    }

    /**
     * Gets the strategy of the Broker object
     * @return Strategy the strategy of the Broker object
     */
    public Strategy getStrat() {
        return strategy;
    }

    /**
     * Notifies the broker of the coin prices
     * @param coin the name of the coin
     */
    public void setInterestPrice(String coin, double price) {
        interestPrices.put(coin, price);
    }

    /**
     * Notifies the broker of the CAD they used
     * 
     * @param coin the name of the coin
     */
    public void setPrice(double price) {
        targetPrice = Double.toString(price);
    }

    /**
     * Gets the amount of CAD the broker used
     * 
     * @param coin the name of the coin
     */
    public String getPrice() {
        return targetPrice;
    }
}
