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
    private DataFetcher df = new DataFetcher();
    private CrytpoDictionary dict = new CrytpoDictionary();

    /**
     * Gets the price of a coin by invoking the required methods in the package
     * @param prices is a HashMap containing coin prices and their names
     * @param dict is a HashMap which is used as a dictionary to convert between coin names, symbols, and ids
     * @param required is an ArrayList which contains required coins for a transaction
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

    /**
     * Getter method which returns crypto-coin dictionary object
     * @return CryptoDictionary object which represents a dictionary that converts ids, symbols, and names
     */
    public HashMap<String, String> getDict() {
        CryptoList dict = new CryptoList();
        return dict.getCryptoDictionary();
    }
}
