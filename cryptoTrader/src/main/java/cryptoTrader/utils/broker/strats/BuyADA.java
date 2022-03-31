package cryptoTrader.utils.broker.strats;

public class BuyADA implements Strategy {
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
        name = "buy ADA";
        op1 = "<";
        op2 = ">";
        coin1 = "BTC";
        price1 = 50000;
        coin2 = "ADA";
        price2 = 2;
        target = "ADA";
        action = "buy";
        amnt = 10;
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
