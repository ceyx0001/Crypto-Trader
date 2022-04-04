package utils.tradingProcess;

import utils.api.PricesFacade;
import utils.broker.Broker;
import utils.tradingProcess.strats.TradeDouble;
import utils.tradingProcess.strats.TradeSingle;

import java.util.HashMap;
import java.util.List;

import utils.tradingProcess.strats.Context;

/**
 * This class is the main trading process class
 *
 * @author Jun Shao, Anthony Tam
 * @since 2022-04-01
 */
public class TradeProcess {

    /**
     * Main trading method which carries out calculations
     * @param brokers is a hashmap storing brokers
     * @param neededCoins is a list of necessary coins
     * @return results of trade
     */
    public TradeResult trade(HashMap<String, Broker> brokers, List<String> neededCoins){
        HashMap<String, Double> prices = new PricesFacade().getPrices(neededCoins);
        TradeResult tr = new TradeResult();
        Context c = null;


        for(Broker broker : brokers.values()){
            if (broker.getStrat().getAction().equals("buy single")) {
                c = new Context(new TradeSingle(), broker, prices, tr);
            } else if (broker.getStrat().getAction().equals("sell single")) {
                c = new Context(new TradeSingle(), broker, prices, tr);
            } else if (broker.getStrat().getAction().equals("buy double")) {
                c = new Context(new TradeDouble(), broker, prices, tr);
            } else {
                c = new Context(new TradeDouble(), broker, prices, tr);
            }
            c.execute();
        }
        return tr;
    }
}
