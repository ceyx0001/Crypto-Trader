package cryptoTrader.utils.operations.main;

/**
 * This class executes the command API for various data operations
 * 
 * @author Jun Shao
 * @since 2022-03-30
 */
public class OperationExecutor {
    /**
     * Executes the data operation given in the parameter
     * @param d the DataOperation
     * @return boolean Determines if the execution was successful
     */
    public boolean execute(DataOperation d) {
        return d.execute();
    }
}
