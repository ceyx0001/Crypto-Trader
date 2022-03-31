package cryptoTrader.utils.operations.main;

/**
 * This class defines the interface that has 
 * the commands of various data operations
 * 
 * @author Jun Shao
 * @since 2022-03-30
 */
public interface DataOperation {
    /**
     * Interface method for the execute command
     * 
     * @param Nothing
     * @return boolean Determines if the execution was successful
     */
    public boolean execute();
}