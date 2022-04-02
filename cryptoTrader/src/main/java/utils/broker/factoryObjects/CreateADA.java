package utils.broker.factoryObjects;

public class CreateADA implements Creator {

    @Override
    public Strategy createStrategy() {
        return new BuyADA();
    }
    
}
