package utils.broker.factoryObjects;

public class BuyBTC implements Strategy {
    private String name;
    private String action;
    private String[] conditions;
    private String target;
    int amnt;

    public BuyBTC() {
        name = "buy BTC";
        action = "buy single";
        target = "BTC";
        amnt = 6;
        conditions = populate();
    }

    private String[] populate() {
        String[] temp = {
                "BTC < 55000"
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
