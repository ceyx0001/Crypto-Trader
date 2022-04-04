package utils.broker.factoryObjects;

/**
 * Subclass of the Creator and overrides the factory method in order to
 * construct and return the Strategy for selling buying BTC coins
 * 
 * @author Anthony Tam
 * @date 2022-03-30
 */
public class CreateBTC implements Creator {
    /**
     * Creates the strategyt associated with buying BTC coins
     * 
     * @param Nothing
     * @return Strategy the strategy for buying BTC coins
     */
    @Override
    public Strategy createStrategy() {
        return new BuyBTC();
    }
    
}
