package cryptoTrader.utils.trade;

import cryptoTrader.utils.api.AvailableCryptoList;
import cryptoTrader.utils.api.DataFetcher;
import cryptoTrader.utils.broker.Broker;
import cryptoTrader.utils.broker.strats.Strategy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Buy implements Transaction {

    public String trade(Broker b, AvailableCryptoList cryptoList, DataFetcher dataFetcher){
        String name = b.getName();
        String op1;
        String op2;
        String coin1;
        int price1;
        String coin2;
        int price2;
        String target;
        int amnt;

        String coin[] = new String[b.getCoins().size()];
        for(int i = 0; i < b.getCoins().size(); i++){
            coin[i] = b.getCoins().get(i);
        }

        Strategy strat = b.getStrat();

        coin1 = strat.getCoin()[0];
        op1 = strat.getCoin()[1];
        price1 = Integer.parseInt(strat.getCoin()[2]);
        target = strat.getTarget()[0];
        amnt = Integer.parseInt(strat.getTarget()[1]);

        String id = cryptoList.getCryptoID(coin1);
        String idT = cryptoList.getCryptoID(target);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateVar = new Date();
        String date = formatter.format(dateVar);
        double targetPrice = dataFetcher.getPriceForCoin(idT, date);

        //if only one operand
        if(strat.getCoin2() == null){

            //check if broker has access to coin information
            Boolean hasCoin = false;
            for(String tempCoin : b.getCoins()){
                if(coin1.equals(tempCoin)){
                    hasCoin = true;
                }
            }
            if(!hasCoin){
                return name + "," + strat.getName() + "," + target + "," + "Buy" + "," + "FAIL" + "," + "FAIL" + "," + date;
            }

            //evaluate strategies
            if(op1.equals(">") && dataFetcher.getPriceForCoin(id, date) > price1){
                return name + "," + strat.getName() + "," + target + "," + "Buy" + "," + amnt + "," + targetPrice + "," + date;
            } else if(op1.equals("<") && dataFetcher.getPriceForCoin(id, date) < price1){
                return name + "," + strat.getName() + "," + target + "," + "Buy" + "," + amnt + "," + targetPrice + "," + date;
            }

        }
        //else two operands
        else {
            coin2 = strat.getCoin2()[0];
            op2 = strat.getCoin2()[1];
            price2 = Integer.parseInt(strat.getCoin2()[2]);
            String id2 = cryptoList.getCryptoID(coin2);

            //check if broker has access to coin information
            Boolean hasCoin1 = false;
            Boolean hasCoin2 = false;
            for(String tempCoin : b.getCoins()){
                if(coin1.equals(tempCoin)){
                    hasCoin1 = true;
                }
                if(coin2.equals(tempCoin)){
                    hasCoin2 = true;
                }
            }

            //evaluate strategies
            if(!hasCoin1 || !hasCoin2){
                return name + "," + strat.getName() + "," + target + "," + "Buy" + "," + "FAIL" + "," + "FAIL" + "," + date;
            }

            if(op1.equals(">") && dataFetcher.getPriceForCoin(id, date) > price1 && op2.equals(">") && dataFetcher.getPriceForCoin(id2, date) > price2){
                return name + "," + strat.getName() + "," + target + "," + "Buy" + "," + amnt + "," + targetPrice + "," + date;
            }
            else if(op1.equals(">") && dataFetcher.getPriceForCoin(id, date) > price1 && op2.equals("<") && dataFetcher.getPriceForCoin(id2, date) < price2){
                return name + "," + strat.getName() + "," + target + "," + "Buy" + "," + amnt + "," + targetPrice + "," + date;
            }
            else if(op1.equals("<") && dataFetcher.getPriceForCoin(id, date) < price1 && op2.equals("<") && dataFetcher.getPriceForCoin(id2, date) < price2){
                return name + "," + strat.getName() + "," + target + "," + "Buy" + "," + amnt + "," + targetPrice + "," + date;
            }
            else if(op1.equals("<") && dataFetcher.getPriceForCoin(id, date) < price1 && op2.equals(">") && dataFetcher.getPriceForCoin(id2, date) > price2){
                return name + "," + strat.getName() + "," + target + "," + "Buy" + "," + amnt + "," + targetPrice + "," + date;
            }
        }


        return null;
    }

}
