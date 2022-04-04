package utils.tradingProcess.strats;

import utils.broker.Broker;
import utils.tradingProcess.TradeResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
        String strat = b.getStrat().getName();
        List<String> interest = b.getCoins();
        String[] conditions = b.getStrat().getConditions();
        String target = b.getStrat().getTarget();
        int amnt = b.getStrat().getAmntBought();

        String[] data1 = conditions[0].split(" ");
        String[] data2 = conditions[1].split(" ");
        String name1 = data1[0];
        String name2 = data2[0];

        if (fail(interest, name1, name2)) {
            tr.addRow(name, strat, target, "Buy", "Null", "Null", date);
        } else {

            String op1 = data1[1];
            String op2 = data2[1];
            double stratPrice1 = Double.valueOf(data1[2]);
            double stratPrice2 = Double.valueOf(data2[2]);
            double realPrice1 = prices.get(name1);
            double realPrice2 = prices.get(name2);
            double targetPrice = prices.get(target);
            
            if (new Compare().compare(realPrice1, stratPrice1, realPrice2, stratPrice2, op1, op2)) {
                tr.addRow(name, strat, target, "Buy", "" + amnt, "" + targetPrice, date);
            }
        }
    }
}
