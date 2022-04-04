package utils.MVC;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which is used to implement an observer design pattern and manage several observers
 *
 * @author Jun Shao
 */
public class Subject {
    private List<Observer> observers = new ArrayList<>();

    /**
     * Method which takes an observer object and attaches it to another observer
     *
     * @param observer is an Observer object
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Method which takes an observer object and detaches it from another observer
     *
     * @param observer is an Observer object
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }


    /**
     * Method which cycles through list of observers and updates them each individually
     */
    public void notifyObservers() {
		for (Observer observer : observers)
			observer.updateObserver(this);
	}
}
