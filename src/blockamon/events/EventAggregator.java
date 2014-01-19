package blockamon.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack's Computer
 * Date: 1/19/14
 * Time: 1:59 PM
 */
public class EventAggregator {
    private List<IEventHandler> _subscribers;

    public EventAggregator() {
        _subscribers = new ArrayList<IEventHandler>();
    }

    public void subscribe(IEventHandler handler) {
        _subscribers.add(handler);
    }

    public void unSubscribe(IEventHandler handler) {
        _subscribers.remove(handler);
    }

    public void broadcast(IEventArgs eventArgs) {
        for(IEventHandler subscriber : _subscribers) {
            if(subscriber.canHandle(eventArgs)) {
                subscriber.handle(eventArgs);
            }
        }
    }
}
