package utils.broker.factoryObjects;

public class CreateBNB implements Creator {

    @Override
    public Strategy createStrategy() {
        return new SellBNB();
    }
    
}
