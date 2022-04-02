package utils.tradingProcess;

import utils.api.CoinPriceFacade;
import utils.broker.Broker;
import utils.tradingProcess.strats.TradeDouble;
import utils.tradingProcess.strats.TradeSingle;

import java.util.HashMap;
import utils.tradingProcess.strats.Context;

public class TradeProcess {
    public TradeResult trade(HashMap<String, Broker> brokers){
        HashMap<String, Double> prices = new CoinPriceFacade().getPrices();
        System.out.println(prices.size());
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
