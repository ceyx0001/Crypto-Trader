package cryptoTrader.utils.broker.strats;

public class StratA implements Strategy {
    String type;
    String buy;
    String sell;
    int buyPrice;
    int sellPrice;

    @Override
    public void create() {
        System.out.println("Strat A created");
    }

    @Override
    public String getType() {
        return type;
    }
}

    
