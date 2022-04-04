package utils.MVC;

/**
 * Interface class which represents the template for an observer which is used
 * to
 * implement an observer design pattern and interface with the user
 *
 * @author Jun Shao
 * @date 2022-04-03
 */
public interface Observer {
    /**
     * Empty observer updating method which takes a Subject object as a parameter
     * 
     * @param subject is a Subject object which manages several observers
     */
    public void updateObserver(Subject subject);
}
