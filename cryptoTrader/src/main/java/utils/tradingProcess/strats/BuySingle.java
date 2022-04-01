package utils.tradingProcess.strats;

import utils.api.CoinPriceFacade;
import utils.broker.Broker;
import utils.tradingProcess.TradeResult;
import utils.tradingProcess.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BuySingle implements Transaction {
    private String name;
    private String strat;
    private List<String> interest;
    private String[] conditions;
    private String target;
    int amnt;

    public BuySingle(Broker b) {
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
            tr.addRow(name, strat, target, "Buy", "Null", "Null", date);
        }

        String[] data = conditions[0].split(" ");
        String name = data[0];
        String op = data[1];
        double reqPrice = Double.valueOf(data[2]);
        double actualPrice = prices.get(name);
        double targetPrice = prices.get(target);

        if (new Compare().compare(actualPrice, reqPrice, op)) {
            tr.addRow(name, strat, target, "Buy", "" + amnt, "" + targetPrice, date);
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
