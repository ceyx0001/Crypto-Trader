package utils.tradingProcess.strats;

import utils.broker.Broker;
import utils.tradingProcess.TradeResult;

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date dateVar = new Date();
        String date = formatter.format(dateVar);
        TradeResult tr = c.getTradeResult();
        HashMap<String, Double> prices = c.getPrices();
        Broker b = c.getBroker();
        String name = b.getName();
        String strat = b.getStrat().getType();
        ArrayList<String> interest = new ArrayList<String>(b.getInterest().keySet());
        String[] conditions = b.getStrat().getConditions();
        String target = b.getStrat().getTarget();
        int amnt = b.getStrat().getAmntBought();

        String[] data1 = conditions[0].split(" ");
        String[] data2 = conditions[1].split(" ");
        String required1 = data1[0];
        String required2 = data2[0];

        if (fail(interest, required1, required2)) {
            tr.addRow(name, strat, target, required1 + "," + required2, "Buy", "Null", "Null", date);
            System.out.println("added");
        } else {

            String op1 = data1[1];
            String op2 = data2[1];
            double stratPrice1 = Double.valueOf(data1[2]);
            double stratPrice2 = Double.valueOf(data2[2]);
            double realPrice1 = prices.get(required1);
            double realPrice2 = prices.get(required2);
            double targetPrice = prices.get(target);

            if (new Compare().compare(realPrice1, stratPrice1, realPrice2, stratPrice2, op1, op2)) {
                tr.addRow(name, strat, target, required1 + "," + required2, "Buy", "" + amnt, "" + targetPrice, date);
            }
        }
    }
}
