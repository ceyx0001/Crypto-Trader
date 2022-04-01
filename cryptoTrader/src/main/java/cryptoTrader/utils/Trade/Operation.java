package cryptoTrader.utils.trade;

import cryptoTrader.utils.api.AvailableCryptoList;
import cryptoTrader.utils.api.DataFetcher;
import cryptoTrader.utils.broker.Broker;

public class Operation {
    private Transaction transaction;

    public Operation(Transaction transaction){
        this.transaction = transaction;
    }

    public String executeTransaction(Broker b, AvailableCryptoList cryptoList, DataFetcher dataFetcher){
        return transaction.trade(b, cryptoList, dataFetcher);
    }
}
