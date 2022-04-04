package utils.broker.factoryObjects;

/**
 * Subclass of the Creator and overrides the factory method in order to
 * construct and return the Strategy for selling BNB coins
 * 
 * @author Anthony Tam
 * @date 2022-03-30
 */
public class CreateBNB implements Creator {
    /**
     * Creates the strategyt associated with selling BNB coins
     * 
     * @param Nothing
     * @return Strategy the strategy for selling BNB coins
     */
    @Override
    public Strategy createStrategy() {
        return new SellBNB();
    }
    
}
