package utils.api;

import java.util.HashMap;
import java.util.List;

public class PricesFacade {
    private DataFetcher df = new DataFetcher();
    private CrytpoDictionary dict = new CrytpoDictionary();

    public HashMap<String, Double> getPrices(List<String> neededCoins) {
        HashMap<String, Double> prices = new HashMap<String, Double>();
        for (String id : neededCoins) {
            String fullName = dict.getCryptoDictionary().get(id);
            prices.put(id, df.getPriceForCoin(fullName));
        }
        return prices;
    }
}
