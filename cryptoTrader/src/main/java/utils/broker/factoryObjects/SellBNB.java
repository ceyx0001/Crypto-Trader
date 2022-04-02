package utils.broker.factoryObjects;

public class SellBNB implements Strategy {
    private String name;
    private String action;
    private String[] conditions;
    private String target;
    int amnt;

    public SellBNB() {
        name = "sell BNB";
        action = "sell single";
        target = "BNB";
        amnt = 8;
        conditions = populate();
    }

    private String[] populate() {
        String[] temp = {
            "DOT > 22",
        };
        return temp;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public String[] getConditions() {
        return conditions;
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public int getAmntBought() {
        return amnt;
    }
}
