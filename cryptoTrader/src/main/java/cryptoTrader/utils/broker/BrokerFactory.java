package cryptoTrader.utils.broker;

import java.util.Arrays;
import java.util.List;

import cryptoTrader.utils.broker.strats.BuyADA;
import cryptoTrader.utils.broker.strats.Strategy;

public class BrokerFactory {
    public Broker getBroker(String name, String coins, String strat) {
        List<String> coinList = Arrays.asList(coins.split(", "));
        return new Broker(name, coinList, getStrategy(strat));
    }
    
    public Strategy getStrategy(String strat) {
        if (strat.equalsIgnoreCase("strategy-a")) {
            return new BuyADA();
        }
        return null;
    }
}
