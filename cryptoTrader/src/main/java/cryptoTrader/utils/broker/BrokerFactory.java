package cryptoTrader.utils.broker;

import java.util.Arrays;
import java.util.List;

import cryptoTrader.utils.broker.strats.*;

public class BrokerFactory {
    public Broker getBroker(String name, String coins, String strat) {
        List<String> coinList = Arrays.asList(coins.split(", "));
        return new Broker(name, coinList, getStrategy(strat));
    }
    
    public Strategy getStrategy(String strat) {
        if (strat.equalsIgnoreCase("buy ADA")) {
            return new BuyADA();
        }
        if (strat.equalsIgnoreCase("buy BTC")) {
            return new BuyBTC();
        }
        if (strat.equalsIgnoreCase("buy LUNA")) {
            return new BuyLUNA();
        }
        if (strat.equalsIgnoreCase("sell ETH")) {
            return new SellETH();
        }

        return null;
    }
}
