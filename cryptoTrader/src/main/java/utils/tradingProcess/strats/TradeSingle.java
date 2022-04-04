package utils.tradingProcess.strats;

import utils.broker.Broker;
import utils.tradingProcess.TradeResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Class which is used to process trades with a single operand
 *
 * @author Jun Shao, Anthony Tam
 * @since 2022-04-01
 */
public class TradeSingle extends Transaction {

    /**
     * Method which carries out trade for a trading strategy with a single operand
     *
     * @param c is a context object
     */
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

        String[] data = conditions[0].split(" ");
        String coinName = data[0];

        if (fail(interest, coinName)) {
            tr.addRow(name, strat, target, "Buy", "Null", "Null", date);
        }

        String op = data[1];
        double stratPrice = Double.valueOf(data[2]);
        double realPrice = prices.get(coinName);
        double targetPrice = prices.get(target);

        if (new Compare().compare(realPrice, stratPrice, op)) {
            tr.addRow(name, strat, target, "Buy", "" + amnt, "" + targetPrice, date);
        }
    }
}
