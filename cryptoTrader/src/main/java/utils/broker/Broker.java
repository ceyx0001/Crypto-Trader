package utils.broker;

import java.util.List;

import utils.broker.factoryObjects.Strategy;

public class Broker {
    private String name;
    private List<String> coins;
    private Strategy strategy;

    public Broker(String name, List<String> coins, Strategy strategy) {
        this.name = name;
        this.coins = coins;
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }

    public List<String> getCoins() {
        return coins;
    }

    public Strategy getStrat() {
        return strategy;
    }
}
