package utils.broker.factoryObjects;

/**
 * Subclass of the Strategy interface which overrides its various methods for its specified purposes, which
 * represents a trading strategy which buys ADA if BTC is less than 60000 and ADA is greater than 1
 *
 * @author Jun Shao
 * @since 2022-04-01
 */
public class BuyADA implements Strategy {
    //initializes variables
    private String name;
    private String action;
    private String[] conditions;
    private String target;
    int amnt;

    /**
     * Constructor method for BuyBTC
     */
    public BuyADA() {
        name = "buy ADA";
        action = "buy double";
        target = "ADA";
        amnt = 10;
        conditions = populate();
    }

    /**
     * Method which returns String array containing the trading strategy's conditions
     * @return String[] that contains the trading strategy's conditions
     */
    private String[] populate() {
        String[] temp = {
                "BTC < 60000",
                "ADA > 1",
        };
        return temp;
    }

    /**
     * Method which returns name of the trading strategy
     * @return String that holds the name of the trading strategy
     */
    @Override
    public String getType() {
        return name;
    }

    /**
     * Method which returns the type of action of the trading strategy
     * @return String that holds the action type of the trading strategy
     */
    @Override
    public String getAction() {
        return action;
    }

    /**
     * Method which returns String array that holds its conditions
     * @return String array that holds its conditions
     */
    @Override
    public String[] getConditions() {
        return conditions;
    }

    /**
     * Method which returns target coin's name
     * @return String representing the name of the coin the trading strategy is targeting
     */
    @Override
    public String getTarget() {
        return target;
    }

    /**
     * Method which returns amount of the target coin that is intended to be bought or sold
     * @return int which represents the amount of a coin that is being transacted
     */
    @Override
    public int getAmntBought() {
        return amnt;
    }
}
