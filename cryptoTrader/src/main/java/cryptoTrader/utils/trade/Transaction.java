package cryptoTrader.utils.trade;

import cryptoTrader.utils.api.AvailableCryptoList;
import cryptoTrader.utils.api.DataFetcher;
import cryptoTrader.utils.broker.Broker;

public interface Transaction {
    public String trade(Broker b, AvailableCryptoList cryptoList, DataFetcher dataFetcher);
}
