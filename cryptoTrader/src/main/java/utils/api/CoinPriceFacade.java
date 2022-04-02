package utils.api;

import java.util.HashMap;

public class CoinPriceFacade {
    public HashMap<String, Double> getPrices() {
        DataFetcher d = new DataFetcher();
        d.today();
        return d.getPriceMap();
    }
}
