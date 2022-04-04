package utils.broker.factoryObjects;

public class BuyADA implements Strategy {
    private String name;
    private String action;
    private String[] conditions;
    private String target;
    int amnt;

    public BuyADA() {
        name = "buy ADA";
        action = "buy double";
        target = "ADA";
        amnt = 10;
        conditions = populate();
    }

    private String[] populate() {
        String[] temp = {
                "BTC < 60000",
                "ADA > 1",
        };
        return temp;
    }

    @Override
    public String getType() {
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
