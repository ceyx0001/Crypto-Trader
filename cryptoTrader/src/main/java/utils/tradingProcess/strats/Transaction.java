package utils.tradingProcess.strats;

import java.util.List;

/**
 * Abstrasct class which represents a transaction.
 *
 * @author Jun Shao
 * @since 2022-04-03
 */
public abstract class Transaction {
    /**
     * Empty trade method which takes a Context object as a parameter.
     *
     * @param context
     */
    public abstract void trade(Context context);

    /**
     * Class which evaluates whether or not necessary coin for trade strategy are in broker's interest list
     *
     * @param interest is a list of coins a broker is interested in
     * @param target is a coin that the broker's trade strategy uses to evaluate the condition
     *
     * @return a boolean representing whether the trade failed or not
     */
    public boolean fail(List<String> interest, String target) {
        for (String coin : interest) {
            if (target.equals(coin)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Class which evaluates whether or not necessary coins for trade strategy are in broker's interest list
     *
     * @param interest is a list of coins a broker is interested in
     * @param target is a coin that the broker's trade strategy uses to evaluate the condition
     * @param target2 is a second coin that the broker's trade strategy uses to evaluate the condition
     *
     * @return a boolean representing whether the transaction failed or not
     */
    public boolean fail(List<String> interest, String target, String target2) {
        boolean noHas1 = true;
        boolean noHas2 = true;

        for (String coin : interest) {
            if (target.equals(coin)) {
                noHas1 = false;
            }
            if (target2.equals(coin)) {
                noHas2 = false;
            }
        }

        return noHas1 || noHas2;
    }
}
