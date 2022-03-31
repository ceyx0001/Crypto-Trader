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
        Strategy s;
        switch (strat) {
            case "Buy ADA":
                s = new BuyADA();
                return s;
            case "Buy BTC":
                s = new BuyBTC();
                return s;
            case "Buy LUNA":
                s = new BuyLUNA();
                return s;
            case "Sell ETH":
                s = new SellETH();
                return s;
            case "Sell BNB":
                s = new SellBNB();
                return s;
            case "None":
                s = new None();
                return s;
        }
        return null;
    }
}
