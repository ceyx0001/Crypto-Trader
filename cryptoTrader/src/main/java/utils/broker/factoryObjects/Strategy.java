package utils.broker.factoryObjects;

/**
 * Interface class which represents the template for a trading strategy
 * 
 * @author Jun Shao, Anthony Tam
 * @date 2022-04-01
 */
public interface Strategy {
    String getType();

    String getAction();

    String[] getConditions();

    String getTarget();

    int getAmntBought();
}
