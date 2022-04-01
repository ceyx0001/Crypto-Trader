package utils.broker.factoryObjects;

public interface Strategy {
    String getName();
    String getAction();
    String[] getConditions();
    String getTarget();
    int getAmntBought();
}
