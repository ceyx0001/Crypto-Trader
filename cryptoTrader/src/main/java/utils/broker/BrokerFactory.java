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
                s = new CreateADA().createStrategy();
                return s;
            case "buy BTC":
                s = new CreateBTC().createStrategy();
                return s;
            case "sell LUNA":
                s = new CreateLUNA().createStrategy();
                return s;
            case "sell BNB":
                s = new CreateBNB().createStrategy();
                return s;
        }
        return null;
    }
}
