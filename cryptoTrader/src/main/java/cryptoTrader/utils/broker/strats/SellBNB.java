package cryptoTrader.utils.broker.strats;

public class SellBNB implements Strategy {
    String name;
    String op;
    String coin;
    String target;
    String action;
    int amnt;
    int price;

    @Override
    public void create() {
        name = "sell BNB";
        op = "<";
        coin = "SOL";
        price = 130;
        target = "BNB";
        action = "sell";
        amnt = 6;
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
