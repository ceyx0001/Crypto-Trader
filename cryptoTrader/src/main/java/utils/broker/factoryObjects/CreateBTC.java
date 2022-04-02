package utils.broker.factoryObjects;

public class CreateBTC implements Creator {

    @Override
    public Strategy createStrategy() {
        return new BuyBTC();
    }
    
}
