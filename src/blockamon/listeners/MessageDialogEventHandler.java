package blockamon.listeners;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 1/6/13
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageDialogEventHandler implements ICustomEventHandler<IMessageDialogListener, MessageDialogEvent>
{
    protected ArrayList<IMessageDialogListener> _listeners = new ArrayList<IMessageDialogListener>();

    @Override
    public void addListener(IMessageDialogListener listener) {
        synchronized (_listeners) {
            _listeners.add(listener);
        }
    }

    @Override
    public void removeListener(IMessageDialogListener listener) {
        synchronized (_listeners) {
            _listeners.remove(listener);
        }
    }

    public synchronized void _fireEvent(MessageDialogEvent eventObject) {
        synchronized (_listeners) {
            for (IMessageDialogListener listener : _listeners)
            {
                listener.displayMessageDialog(eventObject);
            }
        }
    }
}
