package utils.broker;

import java.util.Arrays;
import java.util.List;

import utils.broker.factoryObjects.*;

/**
 * Uses factory methods in order to create Broker objects without having
 * to specify the exact class of the Strategy creation
 * 
 * @author Anthony Tam
 * @date 2022-03-30
 */
public class BrokerFactory {
    /**
     * Creates and returns a Broker object
     * @param name the name of the broker
     * @param coins the coins the broker is interested in
     * @param strat the strategy selected by the broker
     * @return Broker the broker object associated with the parameters
     */
    public Broker getBroker(String name, String coins, String strat) {
        List<String> coinList = Arrays.asList(coins.split(","));
        return new Broker(name, coinList, getStrategy(strat));
    }

    /**
     * Invokes the factory method to create the associated
     * Strategy object
     * @param strat the name of the strategy
     * @return Strategy the selected strategy
     */
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
