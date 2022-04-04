package utils.broker.factoryObjects;

public class SellLUNA implements Strategy {
    private String name;
    private String action;
    private String[] conditions;
    private String target;
    int amnt;

    public SellLUNA() {
        name = "sell LUNA";
        action = "sell double";
        target = "Terra";
        amnt = 60;
        conditions = populate();
    }

    private String[] populate() {
        String[] temp = {
            "BTC > 52000",
            "ETH < 50",
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
