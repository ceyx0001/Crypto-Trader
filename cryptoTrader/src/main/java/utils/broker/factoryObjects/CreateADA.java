package utils.broker.factoryObjects;

/**
 * Subclass of the Creator and overrides the factory method in order to
 * construct and return the Strategy for selling buying ADA coins
 * 
 * @author Anthony Tam
 * @date 2022-03-30
 */
public class CreateADA implements Creator {
    /**
     * Creates the strategyt associated with buying ADA coins
     * @param Nothing
     * @return Strategy the strategy for buying ADA coins
     */
    @Override
    public Strategy createStrategy() {
        return new BuyADA();
    }
    
}
