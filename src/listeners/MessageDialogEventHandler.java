package listeners;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 1/6/13
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageDialogEventHandler implements ICustomEventHandler<MessageDialogListener, MessageDialogEvent>
{
    protected ArrayList<MessageDialogListener> _listeners = new ArrayList<MessageDialogListener>();
    
    @Override
    public void addListener(MessageDialogListener listener) {
        synchronized (_listeners) {
            _listeners.add(listener);
        }
    }

    @Override
    public void removeListener(MessageDialogListener listener) {
        synchronized (_listeners) {
            _listeners.remove(listener);
        }
    }

    public synchronized void _fireEvent(MessageDialogEvent eventObject) {
        synchronized (_listeners) {
            for (MessageDialogListener listener : _listeners)
            {
                listener.displayMessageDialog(eventObject);
            }
        }
    }
}
