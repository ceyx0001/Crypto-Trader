package cryptoTrader.utils.operations.main;

import java.util.ArrayList;
import org.jfree.data.json.impl.JSONObject;

import cryptoTrader.utils.operations.DataOperation;

public class GetBrokers implements DataOperation {
    private BrokerActions actions;

    public GetBrokers(ArrayList<JSONObject> brokers) {
        this.actions = new BrokerActions(brokers);  
    }

    @Override
    public boolean execute() {
        return actions.getFromLocal();
    }
}
