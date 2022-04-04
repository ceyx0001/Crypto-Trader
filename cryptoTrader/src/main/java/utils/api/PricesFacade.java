package utils.api;

import java.util.HashMap;
import java.util.List;

/**
 * This class implements the facade for the methods that
 * fetches a coin's price from CoinGecko's API
 * 
 * @author Ernest Li, Simone Sequeira
 * @date 2022-03-30
 */
public class PricesFacade {
    private DataFetcher df = new DataFetcher();
    private CrytpoDictionary dict = new CrytpoDictionary();

    /**
     * Gets the price of a coin by invoking the required methods in the package
     * @param neededCoins the list of coins required for a trading strategy
     * @return HashMap<String, Double> the map of a coin to its price
     */
    public HashMap<String, Double> getPrices(List<String> neededCoins) {
        HashMap<String, Double> prices = new HashMap<String, Double>();
        for (String id : neededCoins) {
            String fullName = dict.getCryptoDictionary().get(id);
            prices.put(id, df.getPriceForCoin(fullName));
        }
        return prices;
    }
}
