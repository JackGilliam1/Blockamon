package blockamon.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * User: Jack's Computer
 * Date: 1/19/14
 * Time: 1:59 PM
 */
public class EventAggregator {
    private List<EventHandler> _subscriptions;
    private Dictionary<UUID, ArrayList<SingleParameterMethodData>> _singleParameterMethodData;

    public EventAggregator() {
        _subscriptions = new ArrayList<EventHandler>();
        _singleParameterMethodData = new Hashtable<UUID, ArrayList<SingleParameterMethodData>>();
    }

    public void subscribe(EventHandler handler) {
        _subscriptions.add(handler);
    }

    public void unSubscribe(EventHandler handler) {
        _subscriptions.remove(handler);
    }

    private void addSingleParameterMethodData(UUID id, Class<?> parameterType, Method method) {
        ArrayList<SingleParameterMethodData> methodData = _singleParameterMethodData.get(id);
        if(methodData == null) {
            methodData = new ArrayList<SingleParameterMethodData>();
        }
        methodData.add(new SingleParameterMethodData(parameterType, method));
        _singleParameterMethodData.put(id, methodData);
    }

    private ArrayList<SingleParameterMethodData> getSingleParameterMethodData(UUID id) {
        return _singleParameterMethodData.get(id);
    }

    public <TEvent> void broadcast(TEvent event) throws InvocationTargetException, IllegalAccessException {
        for(EventHandler eh : _subscriptions) {

            broadcastToMethod(eh, event);
        }
    }

    private <TEvent> void broadcastToMethod(EventHandler eh, TEvent event)
            throws InvocationTargetException, IllegalAccessException {
        Method[] objectMethods = eh.getClass().getDeclaredMethods();
        for(Method method : objectMethods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes.length == 1) {
                Class<?> firstParameterType = parameterTypes[0];
                if(event.getClass() == firstParameterType) {
                    addSingleParameterMethodData(eh.getUUID(), firstParameterType, method);
                    method.invoke(eh, event);
                }
            }
        }
    }

    private class SingleParameterMethodData {
        private Class<?> _parameterType;
        private Method _method;

        public SingleParameterMethodData(Class<?> parameterType, Method method) {
            _parameterType = parameterType;
            _method = method;
        }

        public Class<?> getParameterType() {
            return _parameterType;
        }

        public Method getMethod() {
            return _method;
        }
    }
}
