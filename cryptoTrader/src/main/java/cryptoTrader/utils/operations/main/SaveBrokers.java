package cryptoTrader.utils.operations.main;

import java.util.ArrayList;
import org.jfree.data.json.impl.JSONObject;

import cryptoTrader.utils.operations.DataOperation;

public class SaveBrokers implements DataOperation {
    private BrokerActions actions;

    public SaveBrokers(ArrayList<JSONObject> brokers) {
        this.actions = new BrokerActions(brokers);  
    }

    @Override
    public boolean execute() {
        return actions.saveToLocal();
    }
}
