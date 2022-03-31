package cryptoTrader.utils.operations.login;

import cryptoTrader.utils.operations.main.DataOperation;

/** 
 * This is the command class that executes the authenticate method of UserActions 
 * 
 * @author Jun Shao
 * @since 2022-03-30
 */
public class Authenticate implements DataOperation {
    private UserActions actions;

    /**
     * Creates a new instance of the authenticate command
     * @param name is the name of the user
     * @param pass is the password of the user
     * @return Nothing
     */
    public Authenticate(String name, String pass) {
        this.actions = new UserActions(name, pass);  
    }

    /** 
     * Executes the authenticate method of UserActions
     * @param Nothing.
     * @return boolean Determines if the execution was successful
     */
    @Override
    public boolean execute() {
        return actions.authenticate();
    }
}
