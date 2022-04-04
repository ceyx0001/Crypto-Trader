package utils.tradingProcess.strats;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Class which is used to process trades with two operands
 *
 * @author Ernest Li, Simone Sequeira
 * @date 2022-04-01
 */
public class TradeDouble extends Transaction {
    private final int actualPriceField = 0;
    private final int operandField = 1;
    private final int stratPriceField = 2;
    private final int firstStrat = 0;
    private final int secondStrat = 1;

    /**
     * Method which carries out trade for a trading strategy with two operands
     *
     * @param c is a context object
     */
    @Override
    public void trade(Context c) {
        // initializes variables
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

        // format of a strategy is [actual price,operand,strategy price]
        // eg BTC < 68 000
        String[] data1 = conditions[firstStrat].split(" ");
        String[] data2 = conditions[secondStrat].split(" ");
        String required1 = data1[actualPriceField];
        String required2 = data2[actualPriceField];

        // check if transaction fails due to missing interested coins
        if (fail(interest, required1, required2)) {
            c.getTradeResult().addRow(name, strat, target, required1 + "," + required2, "Buy", "Null", "Null", date);
            System.out.println("added");
        } else {
            // else, if not fail then compare conditions and add results
            String op1 = data1[operandField];
            String op2 = data2[operandField];
            double stratPrice1 = Double.valueOf(data1[stratPriceField]);
            double stratPrice2 = Double.valueOf(data2[stratPriceField]);
            double realPrice1 = prices.get(required1);
            double realPrice2 = prices.get(required2);
            double targetPrice = prices.get(target);

            // determines if the strategy statement is true and adds the result to the
            // context
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
