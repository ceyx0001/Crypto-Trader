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
        //initializing variables
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

        String[] data = conditions[0].split(" ");
        String required = data[0];

        //check if transaction fails due to missing intereset coins
        if (fail(interest, required)) {
            c.getTradeResult().addRow(name, strat, target, required, "Buy", "Null", "Null", date);
        } else {
            //if not failed, compare conditions and add results
            String op = data[1];
            double stratPrice = Double.valueOf(data[2]);
            double realPrice = prices.get(required);
            double targetPrice = prices.get(target);

            if (new Compare().compare(realPrice, stratPrice, op)) {
                c.getTradeResult().addRow(name, strat, target, required, "Buy", "" + amnt, "" + targetPrice, date);
            } else {
                c.getTradeResult().addRow(name, strat, target, required, "Buy", "" + 0, "" + targetPrice, date);
            }
        }
    }
}
