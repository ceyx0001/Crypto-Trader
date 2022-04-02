package utils.broker.factoryObjects;

public class CreateLUNA implements Creator {

    @Override
    public Strategy createStrategy() {
        return new SellLUNA();
    }
    
}
