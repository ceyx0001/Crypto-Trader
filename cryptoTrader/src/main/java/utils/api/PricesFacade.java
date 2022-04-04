package utils.api;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the facade for the methods that
 * fetches a coin's price from CoinGecko's API
 * 
 * @author Ernest Li, Simone Sequeira
 * @date 2022-03-30
 */
public class PricesFacade {
    /**
     * Gets the price of a coin by invoking the required methods in the package
     * @param neededCoins the list of coins required for a trading strategy
     * @return HashMap<String, Double> the map of a coin to its price
     */
    public void getPrices(HashMap<String, Double> prices, HashMap<String, String> dict, ArrayList<String> required) {
        DataFetcher df = new DataFetcher();
        for (String sym : required) {
            String id = dict.get(sym);
            if (prices.get(sym) == null) {
                prices.put(sym, df.getPriceForCoin(id));
            }
        }
    }

    public HashMap<String, String> getDict() {
        CryptoList dict = new CryptoList();
        return dict.getCryptoDictionary();
    }
}
