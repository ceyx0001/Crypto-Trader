package cryptoTrader.utils.broker.strats;

public class None implements Strategy {
    String name;

    @Override
    public void create() {
        name = "None";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String printStrat() {
        return "No action.";
    }
}
