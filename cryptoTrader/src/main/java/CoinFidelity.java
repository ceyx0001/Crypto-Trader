import cryptoTrader.utils.trade.TradeController;
import cryptoTrader.utils.trade.TradeModel;
import cryptoTrader.utils.trade.TradeView;

public class CoinFidelity {
    public static void main(String[] args) {
        TradeView view = TradeView.getInstance();
        TradeModel model = new TradeModel();
        TradeController control = new TradeController(model, view);
        control.initGUI();
    }
}
