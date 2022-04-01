package cryptoTrader.utils.trade;

import cryptoTrader.utils.api.AvailableCryptoList;
import cryptoTrader.utils.api.DataFetcher;
import cryptoTrader.utils.broker.Broker;
import cryptoTrader.utils.broker.strats.Strategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TradeProcess {

    public TradeProcess(){}

    public String[] trade(HashMap<String, Broker> brokersParameter, AvailableCryptoList cryptoList, DataFetcher dataFetcher){
        Broker[] brokers = new Broker[brokersParameter.size()];
        Operation operation;
        String[] returnStr;
        int  count = 0;


        for (String key : brokersParameter.keySet()) {
            brokers[count] = brokersParameter.get(key);
            count++;
        }

        returnStr = new String[count];
        count = 0;

        for(Broker broker : brokers){
            if(broker.getStrat().getType().equals("buy")){
                operation = new Operation(new Buy());
            } else {
                operation = new Operation(new Sell());
            }
            returnStr[count] = operation.executeTransaction(broker, cryptoList, dataFetcher);
            System.out.println(returnStr[count]);
            count++;
        }
        return returnStr;
    }

}
