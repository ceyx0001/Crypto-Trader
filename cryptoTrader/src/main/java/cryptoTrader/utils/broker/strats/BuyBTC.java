package cryptoTrader.utils.broker.strats;

public class BuyBTC implements Strategy {
    String name;
    String op1;
    String coin1;
    String target;
    String action;
    int amnt;
    int price1;

    public BuyBTC() {
        name = "buy BTC";
        op1 = "<";
        coin1 = "BTC";
        price1 = 48000;
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
        return name + ": " + coin1 + op1 + price1 + " -> " + action + " " + amnt + " " + target;
    }

    @Override
    public String[] getCoin() {
        return new String[] {coin1, op1, "" + price1};
    }

    @Override
    public String[] getCoin2() {
        return null;
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
