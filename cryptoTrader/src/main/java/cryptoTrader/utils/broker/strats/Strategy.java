package cryptoTrader.utils.broker.strats;

public interface Strategy {
    String getName();
    String printStrat();
    String[] getCoin();
    String[] getCoin2();
    String[] getTarget();
    String getType();
}
