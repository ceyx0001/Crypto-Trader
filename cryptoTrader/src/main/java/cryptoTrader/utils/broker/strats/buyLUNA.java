package cryptoTrader.utils.broker.strats;

public class buyLUNA implements Strategy {
    String name;
    String op1;
    String op2;
    String coin1;
    String coin2;
    String target;
    String action;
    int price1;
    int price2;
    int amnt;

    @Override
    public void create() {
        name = "buy LUNA";
        op1 = ">";
        op2 = "<";
        coin1 = "BTC";
        price1 = 55000;
        coin2 = "ETH";
        price2 = 3500;

        action = "buy";
        target = "LUNA";
        amnt = 60;

    }

    @Override
    public String getName() { return name; }

    @Override
    public String printStrat()  {
        return name + ": " + coin1 + op1 + price1 + " & " + coin2 + op2 + price2 + " -> " + action + " " + amnt + " " + target;
    }

}

    
