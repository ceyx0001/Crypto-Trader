package utils.broker.factoryObjects;

public interface Strategy {
    String getType();
    String getAction();
    String[] getConditions();
    String getTarget();
    int getAmntBought();
}
