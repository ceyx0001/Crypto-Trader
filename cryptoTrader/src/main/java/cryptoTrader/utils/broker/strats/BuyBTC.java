package cryptoTrader.utils.broker.strats;

public class BuyBTC implements Strategy {
    String name;
    String op;
    String coin;
    String target;
    String action;
    int amnt;
    int price;

    @Override
    public void create() {
        name = "buy BTC";
        op = "<";
        coin = "BTC";
        price = 48000;
        target = "BTC";
        action = "buy";
        amnt = 5;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String printStrat() {
        return name + ": " + coin + op + price + " -> " + action + " " + amnt + " " + target;
    }
}
