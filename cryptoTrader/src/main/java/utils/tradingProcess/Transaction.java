package utils.tradingProcess;

import utils.broker.Broker;

public interface Transaction {
    public void trade(TradeResult tr, Broker b);
}
