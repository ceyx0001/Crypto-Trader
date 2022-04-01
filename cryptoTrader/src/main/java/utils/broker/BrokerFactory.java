package utils.broker;

import java.util.Arrays;
import java.util.List;

import utils.broker.factoryObjects.*;

public class BrokerFactory {
    public Broker getBroker(String name, String coins, String strat) {
        List<String> coinList = Arrays.asList(coins.split(","));
        return new Broker(name, coinList, getStrategy(strat));
    }

    public Strategy getStrategy(String strat) {
        Strategy s;
        switch (strat) {
            case "buy ADA":
                s = new BuyADA();
                return s;
            case "buy BTC":
                s = new BuyBTC();
                return s;
            case "buy LUNA":
                s = new SellLUNA();
                return s;
        }
        return null;
    }
}
