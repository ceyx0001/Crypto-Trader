package utils.tradingProcess;

import utils.api.CryptoList;
import utils.api.DataFetcher;
import utils.broker.Broker;
import utils.tradingProcess.strats.BuyDouble;
import utils.tradingProcess.strats.BuySingle;
import utils.tradingProcess.strats.SellDouble;
import utils.tradingProcess.strats.SellSingle;

import java.util.HashMap;

public class TradeProcess {

    public TradeProcess(){}

    public TradeResult trade(HashMap<String, Broker> brokers, CryptoList cryptoList, DataFetcher dataFetcher){
        TradeResult tr = new TradeResult();
        Transaction transaction;

        for(Broker broker : brokers.values()){
            if (broker.getStrat().getAction().equals("buy single")) {
                transaction = new BuySingle(broker);
            } else if (broker.getStrat().getAction().equals("sell single")) {
                transaction = new SellSingle(broker);
            } else if (broker.getStrat().getAction().equals("buy double")) {
                transaction = new BuyDouble(broker);
            } else {
                transaction = new SellDouble(broker);
            }

            transaction.trade(tr, broker);
        }

        return tr;
    }
}