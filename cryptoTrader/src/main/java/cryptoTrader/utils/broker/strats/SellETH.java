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

    public SellETH() {
        name = "sell ETH";
        op1 = ">";
        op2 = ">";
        coin1 = "ADA";
        price1 = 1;
        coin2 = "ETH";
        price2 = 2500;
        target = "BTC";
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

    @Override
    public String[] getCoin() {
        return new String[] {coin1, op1, "" + price1};
    }

    @Override
    public String[] getCoin2() {
        return new String[] {coin2, op2, "" + price2};
    }

    @Override
    public String[] getTarget() {
        return new String[] {target, "" + amnt};
    }

    @Override
    public String getType(){
        return action;
    }
}
