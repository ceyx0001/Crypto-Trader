package utils.tradingProcess;

import utils.api.PricesFacade;
import utils.broker.Broker;
import utils.tradingProcess.strats.TradeDouble;
import utils.tradingProcess.strats.TradeSingle;

import java.util.ArrayList;
import java.util.HashMap;

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
    private PricesFacade pf;
    ArrayList<String> required;
    
    /**
     * Returns the instance of the TradeManager
     * 
     * @param Nothing
     * @return TradeManager the instance of the TradeManager
     */
    public static TradeManager getInstance(ArrayList<String> required) {
        if (tm == null) {
            tm = new TradeManager(required);
        }
        required.addAll(required);
        return tm;
    }

    /**
	 * Constructor for CryptoDictionary object
	 * 
	 * @param Nothing
	 * @return Nothing
	 */
	private TradeManager(ArrayList<String> required) {
        pf = new PricesFacade();
		prices = new HashMap<String, Double>();
        dict = pf.getDict();
        this.required = required;
	}

    /**
     * Main trading method which carries out calculations
     * @param brokers is a hashmap storing brokers
     * @return TradeResult results of the trade
     */
    public TradeResult trade(HashMap<String, Broker> brokers) {
        for (String name : brokers.keySet()) {
            Broker b = brokers.get(name);
            prices.put(b.getStrat().getTarget(), null);
            for (String coin : b.getInterest().keySet()) {
                pf.getPrices(prices, dict, required);
                Double targetCoinPrice = prices.get(b.getStrat().getTarget());
                b.setPrice(targetCoinPrice);
                if (brokers.get(name) != null) {
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
