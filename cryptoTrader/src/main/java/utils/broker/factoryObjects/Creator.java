package utils.broker.factoryObjects;

/**
 * Defines the Creator interface which constructs and returns a Strategy
 * 
 * @author Anthony Tam
 * @date 2022-03-30
 */
public interface Creator {
    /**
     * The abstract method for creating a trading strategy
     * 
     * @param Nothing
     * @return Strategy the trading strategy
     */
    public abstract Strategy createStrategy();
}
