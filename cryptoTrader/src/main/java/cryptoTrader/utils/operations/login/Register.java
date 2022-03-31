package cryptoTrader.utils.operations.login;

import cryptoTrader.utils.operations.main.DataOperation;

/**
 * This is the command class that executes the saveUser method of UserActions
 * 
 * @author Jun Shao
 * @since 2022-03-30
 */
public class Register implements DataOperation {
    private UserActions actions;

    /**
     * Creates a new instance of the register command
     * 
     * @param name is the name of the user
     * @param pass is the password of the user
     */
    public Register(String name, String pass) {
        this.actions = new UserActions(name, pass);  
    }

    /**
     * Executes the saveUser method of UserActions
     * @param Nothing
     * @return boolean Determines if the execution was successful
     */
    @Override
    public boolean execute() {
        return actions.saveUser();
    }
}
