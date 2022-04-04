package utils.tradingProcess;

import utils.api.PricesFacade;
import utils.broker.Broker;
import utils.tradingProcess.strats.TradeDouble;
import utils.tradingProcess.strats.TradeSingle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import utils.tradingProcess.strats.Context;

/**
 * This class is the main trading process class
 *
 * @author Jun Shao, Anthony Tam
 * @since 2022-04-01
 */
public class TradeManager {
    private static TradeManager tm;
    private HashMap<String, Double> prices;
    private HashMap<String, String> dict;
    private HashSet<String> required;
    private PricesFacade pf;
    
    /**
     * Returns the instance of the TradeManager
     * 
     * @param Nothing
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
	 * 
	 * @param Nothing
	 * @return Nothing
	 */
	private TradeManager() {
        pf = new PricesFacade();
		prices = new HashMap<String, Double>();
        required = new HashSet<String>();
        dict = pf.getDict();
	}

    /**
     * Main trading method which carries out calculations
     * @param brokers is a hashmap storing brokers
     * @return TradeResult results of the trade
     */
    public TradeResult trade(HashMap<String, Broker> brokers) {
        for (Broker b : brokers.values()) {
            String targetCoin = b.getStrat().getTarget();
            if (!required.contains(targetCoin)) {
                required.add(targetCoin);
            }
            for (String interestCoin : b.getInterest().keySet()) {
                if (!required.contains(interestCoin)) {
                    required.add(interestCoin);
                }
            }
            pf.getPrices(prices, dict, new ArrayList<String>(required));
            for (String coin : b.getInterest().keySet()) {
                Double targetCoinPrice = prices.get(b.getStrat().getTarget());
                b.setPrice(targetCoinPrice);
                if (brokers.get(b.getName()) != null) {
                    b.setInterestPrice(coin, prices.get(coin));
                }
            }
        }
        TradeResult tr = new TradeResult();
        ArrayList<String> available = new ArrayList<String>(prices.keySet());
        Context c = null;

        for(Broker broker : brokers.values()){
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
