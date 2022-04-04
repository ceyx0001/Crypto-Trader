package utils.tradingProcess.strats;

import utils.broker.Broker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Class which is used to process trades with a single operand
 *
 * @author Jun Shao, Anthony Tam
 * @date 2022-04-01
 */
public class TradeSingle extends Transaction {
    private final int actualPriceField = 0;
    private final int operand = 1;
    private final int stratPriceField = 2;

    /**
     * Method which carries out trade for a trading strategy with a single operand
     *
     * @param c is a context object
     */
    @Override
    public void trade(Context c) {
        // initializing variables
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateVar = new Date();
        String date = formatter.format(dateVar);
        HashMap<String, Double> prices = c.getPrices();
        Broker b = c.getBroker();
        String name = b.getName();
        String strat = b.getStrat().getType();
        ArrayList<String> interest = new ArrayList<String>(b.getInterest().keySet());
        String[] conditions = b.getStrat().getConditions();
        String target = b.getStrat().getTarget();
        int amnt = b.getStrat().getAmntBought();

        // format of a strategy is [actual price,operand,strategy price]
        // eg BTC < 68 000
        String[] data = conditions[0].split(" ");
        String required = data[actualPriceField];

        // check if transaction fails due to missing intereset coins
        if (fail(interest, required)) {
            c.getTradeResult().addRow(name, strat, target, required, "Buy", "Null", "Null", date);
        } else {
            // if not failed, compare conditions and add results
            String op = data[operand];
            double stratPrice = Double.valueOf(data[stratPriceField]);
            double realPrice = prices.get(required);
            double targetPrice = prices.get(target);

            // determines if the strategy statement is true and adds the result to the
            // context
            if (new Compare().compare(realPrice, stratPrice, op)) {
                c.getTradeResult().addRow(name, strat, target, required, "Buy", "" + amnt, "" + targetPrice, date);
            } else {
                c.getTradeResult().addRow(name, strat, target, required, "Buy", "" + 0, "" + targetPrice, date);
            }
        }
    }
}
