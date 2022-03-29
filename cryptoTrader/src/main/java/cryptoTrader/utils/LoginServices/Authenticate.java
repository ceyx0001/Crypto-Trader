package cryptoTrader.utils.LoginServices;

import cryptoTrader.utils.DataUtils.DataOperation;

public class Authenticate implements DataOperation {
    private UserActions actions;

    public Authenticate(String name, String pass) {
        this.actions = new UserActions(name, pass);  
    }

    @Override
    public boolean execute() {
        return actions.authenticate();
    }
}
