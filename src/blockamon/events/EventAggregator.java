package blockamon.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack's Computer
 * Date: 1/19/14
 * Time: 1:59 PM
 */
public class EventAggregator {
    private List _subscriptions;

    public EventAggregator() {
        _subscriptions = new ArrayList();
    }

    public void subscribe(Object handler) {
        _subscriptions.add(handler);
    }

    public void unSubscribe(Object handler) {
        _subscriptions.remove(handler);
    }

    public <TEvent> void broadcast(TEvent event) throws InvocationTargetException, IllegalAccessException {
        for(Object obj : _subscriptions) {
            Method[] objectMethods = obj.getClass().getDeclaredMethods();
            for(Method method : objectMethods) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if(parameterTypes.length == 1)
                {
                    Class<?> parameterTypeOne = parameterTypes[0];
                    if(event.getClass() == parameterTypeOne) {
                        method.invoke(obj, event);
                    }
                }
            }
        }
    }
}
