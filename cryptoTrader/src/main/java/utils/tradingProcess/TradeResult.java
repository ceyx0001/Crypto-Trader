package utils.tradingProcess;

import java.util.ArrayList;
import java.util.List;

public class TradeResult {
    private List<String[]> table;
    
    protected TradeResult() {
        table = new ArrayList<String[]>();
    }

    public void addRow(String name, String strat, String coin, String action, String amnt, String price, String date) {
        int columns = 7;
        for (int i = 0; i < columns; i++) {
            String[] row = {name, strat, coin, action, amnt, price, date};
            table.add(row);
        }
    }

    public String[][] getTable() {
        int rows = table.size();
        String[][] result = new String[rows][7];
        for (int i = 0; i < rows; i++) {
            result[i] = table.get(0);
        }
        return result;
    }
}
