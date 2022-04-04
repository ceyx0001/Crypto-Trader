package utils.tradingProcess;

import utils.api.PricesFacade;
import utils.broker.Broker;
import utils.tradingProcess.strats.TradeDouble;
import utils.tradingProcess.strats.TradeSingle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import utils.tradingProcess.strats.Context;

/**
 * This class is the main trading process class
 *
 * @author Jun Shao
 * @since 2022-04-01
 */
public class TradeManager {
    private static TradeManager tm;
    private HashMap<String, Double> prices;
    private HashMap<String, String> dict;
    private HashSet<String> required;
    private PricesFacade pf;

    /**
     * Method which returns the instance of the TradeManager
     *
     * @return TradeManager the instance of the TradeManager
     */
    public static TradeManager getInstance() {
        if (tm == null) {
            tm = new TradeManager();
        }
        return tm;
    }

    /**
     * Constructor for CryptoDictionary object
     */
    private TradeManager() {
        pf = new PricesFacade();
        prices = new HashMap<String, Double>();
        required = new HashSet<String>();
        dict = pf.getDict();
    }

    /**
     * Main trading method which carries out calculations
     * 
     * @param brokers is a hashmap storing brokers
     * @return TradeResult results of the trade
     */
    public TradeResult trade(HashMap<String, Broker> brokers) {
        for (Broker b : brokers.values()) { // iterate through the system's brokers
            String targetCoin = b.getStrat().getTarget(); // get the strategy coin
            if (!required.contains(targetCoin)) {
                required.add(targetCoin); // add target coin to the price fetching list
            }
            for (String interestCoin : b.getInterest().keySet()) { // get the broker's coin list
                if (!required.contains(interestCoin)) {
                    required.add(interestCoin); // add coin list to the price fetching list
                }
            }
            pf.getPrices(prices, dict, new ArrayList<String>(required)); // fetch the prices of the coins
            for (String coin : b.getInterest().keySet()) { // notify the brokers by passing them the prices
                Double targetCoinPrice = prices.get(b.getStrat().getTarget());
                b.setPrice(targetCoinPrice);
                if (brokers.get(b.getName()) != null) {
                    b.setInterestPrice(coin, prices.get(coin));
                }
            }
        }

        // create a new trade result to store the information of the trade
        TradeResult tr = new TradeResult();
        ArrayList<String> available = new ArrayList<String>(prices.keySet());
        Context c = null;

        // main logic loop that executes the strategy for each broker in the broker
        // hashmap (strategy design), creates the strategy context
        for (Broker broker : brokers.values()) {
            if (broker.getStrat().getAction().equals("buy single")) {
                c = new Context(new TradeSingle(), broker, prices, tr, available);
            } else if (broker.getStrat().getAction().equals("sell single")) {
                c = new Context(new TradeSingle(), broker, prices, tr, available);
            } else if (broker.getStrat().getAction().equals("buy double")) {
                c = new Context(new TradeDouble(), broker, prices, tr, available);
            } else {
                c = new Context(new TradeDouble(), broker, prices, tr, available);
            }
            c.execute();
        }

        if (c == null) {
            System.out.println("Error creating context");
        }

        return tr;
    }
}
