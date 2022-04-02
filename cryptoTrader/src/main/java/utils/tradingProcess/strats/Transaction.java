package utils.tradingProcess.strats;

import java.util.List;

public abstract class Transaction {
    public abstract void trade(Context context);

    public boolean fail(List<String> interest, String target) {
        for (String coin : interest) {
            if (target.equals(coin)) {
                return false;
            }
        }
        return true;
    }
}
