package utils.tradingProcess.strats;

import utils.broker.Broker;
import utils.tradingProcess.TradeResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TradeSingle extends Transaction {
    @Override
    public void trade(Context c) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateVar = new Date();
        String date = formatter.format(dateVar);
        TradeResult tr = c.getTradeResult();
        HashMap<String, Double> prices = c.getPrices();
        Broker b = c.getBroker();
        String name = b.getName();
        String strat = b.getStrat().getName();
        List<String> interest = b.getCoins();
        String[] conditions = b.getStrat().getConditions();
        String target = b.getStrat().getTarget();
        int amnt = b.getStrat().getAmntBought();
        

        if (fail(interest, target)) {
            tr.addRow(name, strat, target, "Buy", "Null", "Null", date);
        }

        String[] data = conditions[0].split(" ");
        String coinName = data[0];
        String op = data[1];
        double reqPrice = Double.valueOf(data[2]);
        double actualPrice = prices.get(coinName);
        double targetPrice = prices.get(target);

        if (new Compare().compare(actualPrice, reqPrice, op)) {
            tr.addRow(name, strat, target, "Buy", "" + amnt, "" + targetPrice, date);
        }
    }
}
