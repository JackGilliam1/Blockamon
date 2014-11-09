package blockamon.listeners;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 3/10/13
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICustomEventHandler<T extends EventListener, U extends EventObject> {
    public void addListener(T listener);
    public void removeListener(T listener);

    public void _fireEvent(U eventObject);
}
