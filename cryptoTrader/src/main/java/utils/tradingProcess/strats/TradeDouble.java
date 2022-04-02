package utils.tradingProcess.strats;

import utils.broker.Broker;
import utils.tradingProcess.TradeResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TradeDouble extends Transaction {
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

        String[] data1 = conditions[0].split(" ");
        String[] data2 = conditions[1].split(" ");
        String name1 = data1[0];
        String name2 = data2[0];
        String op1 = data1[1];
        String op2 = data2[1];
        double rp1 = Double.valueOf(data1[2]);
        double rp2 = Double.valueOf(data2[2]);
        double ap1 = c.getPrices().get(name1);
        double ap2 = prices.get(name2);
        double targetPrice = prices.get(target);

        if (new Compare().compare(ap1, rp1, ap2, rp2, op1, op2)) {
            tr.addRow(name, strat, target, "Buy", "" + amnt, "" + targetPrice, date);
        }
    }
}
