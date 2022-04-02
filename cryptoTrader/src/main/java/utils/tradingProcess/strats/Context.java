package utils.tradingProcess.strats;

import java.util.HashMap;

import utils.broker.Broker;
import utils.tradingProcess.TradeResult;

public class Context {
    private Transaction transaction;
    private TradeResult tr;
    private Broker b;
    private HashMap<String, Double> prices;

    public Context(Transaction transaction, Broker b, HashMap<String, Double> prices, TradeResult tr) {
        this.transaction = transaction;
        this.b = b;
        this.prices = prices;
        this.tr = tr;
    }

    public Broker getBroker() {
        return b;
    }

    public HashMap<String, Double> getPrices() {
        return prices;
    }

    public TradeResult getTradeResult() {
        return tr;
    }

    public void execute() {
        transaction.trade(this);
    }
}
