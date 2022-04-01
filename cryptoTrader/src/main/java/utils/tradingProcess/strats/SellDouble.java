package utils.tradingProcess.strats;

import utils.api.CoinPriceFacade;
import utils.broker.Broker;
import utils.tradingProcess.TradeResult;
import utils.tradingProcess.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SellDouble implements Transaction {
    private String name;
    private String strat;
    private List<String> interest;
    private String[] conditions;
    private String target;
    int amnt;

    public SellDouble(Broker b) {
        name = b.getName();
        interest = b.getCoins();
        conditions = b.getStrat().getConditions();
        target = b.getStrat().getTarget();
    }

    @Override
    public void trade(TradeResult tr, Broker b) {
        HashMap<String, Double> prices = new CoinPriceFacade().getPrices();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateVar = new Date();
        String date = formatter.format(dateVar);

        if (fail()) {
            tr.addRow(name, strat, target, "Sell", "Null", "Null", date);
        }

        String[] data1 = conditions[0].split(" ");
        String[] data2 = conditions[1].split(" ");
        String name1 = data1[0];
        String name2 = data2[0];
        String op1 = data1[1];
        String op2 = data2[1];
        double rp1 = Double.valueOf(data1[2]);
        double rp2 = Double.valueOf(data2[2]);
        double ap1 = prices.get(name1);
        double ap2 = prices.get(name2);
        double targetPrice = prices.get(target);

        if (new Compare().compare(ap1, rp1, ap2, rp2, op1, op2)) {
            tr.addRow(name, strat, target, "Sell", "" + amnt, "" + targetPrice, date);
        }
    }

    private boolean fail() {
        for (String coin : interest) {
            if (target.equals(coin)) {
                return false;
            }
        }
        return true;
    }
}
