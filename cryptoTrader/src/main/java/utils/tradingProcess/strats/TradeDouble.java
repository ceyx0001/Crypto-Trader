package utils.tradingProcess.strats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Class which is used to process trades with two operands
 *
 * @author Jun Shao, Anthony Tam
 * @since 2022-04-01
 */
public class TradeDouble extends Transaction {
    /**
     * Method which carries out trade for a trading strategy with two operands
     *
     * @param c is a context object
     */
    @Override
    public void trade(Context c) {
        //initializes variables
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateVar = new Date();
        String date = formatter.format(dateVar);
        HashMap<String, Double> prices = c.getPrices();
        String name = c.getBroker().getName();
        String strat = c.getBroker().getStrat().getType();
        ArrayList<String> interest = new ArrayList<String>(c.getBroker().getInterest().keySet());
        String[] conditions = c.getBroker().getStrat().getConditions();
        String target = c.getBroker().getStrat().getTarget();
        int amnt = c.getBroker().getStrat().getAmntBought();

        String[] data1 = conditions[0].split(" ");
        String[] data2 = conditions[1].split(" ");
        String required1 = data1[0];
        String required2 = data2[0];

        //check if transaction fails due to missing interested coins
        if (fail(interest, required1, required2)) {
            c.getTradeResult().addRow(name, strat, target, required1 + "," + required2, "Buy", "Null", "Null", date);
            System.out.println("added");
        } else {
            //else, if not fail then compare conditions and add results
            String op1 = data1[1];
            String op2 = data2[1];
            double stratPrice1 = Double.valueOf(data1[2]);
            double stratPrice2 = Double.valueOf(data2[2]);
            double realPrice1 = prices.get(required1);
            double realPrice2 = prices.get(required2);
            double targetPrice = prices.get(target);

            if (new Compare().compare(realPrice1, stratPrice1, realPrice2, stratPrice2, op1, op2)) {
                c.getTradeResult().addRow(name, strat, target, required1 + "," + required2, "Buy", "" + amnt,
                        "" + targetPrice, date);
            } else {
                c.getTradeResult().addRow(name, strat, target, required1 + "," + required2, "Buy", "" + 0,
                        "" + targetPrice, date);
            }
        }
    }
}
