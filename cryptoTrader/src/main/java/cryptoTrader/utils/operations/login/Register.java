package cryptoTrader.utils.operations.login;

import cryptoTrader.utils.operations.DataOperation;

public class Register implements DataOperation {
    private UserActions actions;

    public Register(String user, String pass) {
        this.actions = new UserActions(user, pass);  
    }

    @Override
    public boolean execute() {
        return actions.register();
    }
}
