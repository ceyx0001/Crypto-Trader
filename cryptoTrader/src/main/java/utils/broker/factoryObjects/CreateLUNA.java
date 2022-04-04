package utils.broker.factoryObjects;

/**
 * Subclass of the Creator and overrides the factory method in order to
 * construct and return the Strategy for selling LUNA coins
 * 
 * @author Anthony Tam
 * @date 2022-03-30
 */
public class CreateLUNA implements Creator {
    /**
     * Creates the strategyt associated with selling LUNA coins
     * 
     * @param Nothing
     * @return Strategy the strategy for selling LUNA coins
     */
    @Override
    public Strategy createStrategy() {
        return new SellLUNA();
    }
    
}
