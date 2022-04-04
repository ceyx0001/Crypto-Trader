package utils.tradingProcess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the result of a trade.
 *
 * @author Jun Shao
 * @since 2022-04-03
 */
public class TradeResult {
    private List<String[]> table;

    protected TradeResult() {
        table = new ArrayList<String[]>();
    }

    /**
     * Method which adds a new row to trade results using provided parameters.
     * 
     * @param name   is the name of the broker
     * @param strat  is the name of the strat
     * @param coin   is the name of the coin that was bought/sold
     * @param action is the type of transaction (buy/sell)
     * @param amnt   is the amount of the specified coin that was bought or sold
     * @param price  is the price of the coin at the time of the transaction
     * @param date   is the date of the transaction
     */
    public void addRow(String name, String strat, String coin, String required, String action, String amnt,
            String price, String date) {
        String[] row = { name, strat, coin, required, action, amnt, price, date };
        table.add(row);
    }

    /**
     * Accessor method which returns table as a 2D string array.
     * @param Nothing
     * @return String[][] the table representing the TradeResult
     */
    public String[][] getTable() {
        int rows = table.size();
        String[][] result = new String[rows][8];
        for (int i = 0; i < rows; i++) {
            result[i] = table.get(i);
        }
        return result;
    }
}
