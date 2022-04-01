package utils.tradingProcess;

import java.util.HashMap;

import utils.broker.Broker;

public interface Transaction {
    public void trade(TradeResult tr, Broker b, HashMap<String, Double> prices);
}
