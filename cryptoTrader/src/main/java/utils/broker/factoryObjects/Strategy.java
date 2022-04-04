package utils.broker.factoryObjects;

/**
 * Interface class which represents the template for a trading strategy
 */
public interface Strategy {
    String getType();
    String getAction();
    String[] getConditions();
    String getTarget();
    int getAmntBought();
}
