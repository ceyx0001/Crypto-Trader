package cryptoTrader.utils.broker.strats;

public class SellETH implements Strategy {
    String name;
    String op1;
    String op2;
    String coin1;
    int price1;
    String coin2;
    int price2;
    String target;
    String action;
    int amnt;

    @Override
    public void create() {
        name = "sell ETH";
        op1 = ">";
        op2 = ">";
        coin1 = "ADA";
        price1 = 1;
        coin2 = "BTC";
        price2 = 50000;
        target = "ETH";
        action = "sell";
        amnt = 3;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String printStrat() {
        return name + ": " + coin1 + op1 + price1 + " & " + coin2 + op2 + price2 + " -> " + action + " " + amnt + " "
                + target;
    }
}
