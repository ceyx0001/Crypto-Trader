package cryptoTrader.utils.operations.main;

import java.util.ArrayList;
import org.jfree.data.json.impl.JSONObject;

public class BrokerActions {
    private ArrayList<JSONObject> brokers;

    public BrokerActions(ArrayList<JSONObject> brokers) {
        this.brokers = brokers;
    }

    public boolean saveToLocal() {
        return true;
    }

    public boolean getFromLocal() {
        return true;
    }
}
