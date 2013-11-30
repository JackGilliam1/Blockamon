package listeners;

import java.util.EventListener;

/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 1/6/13
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
//TODO: Use this event listener instead of throwing a MessageDialogException
public interface MessageDialogListener extends EventListener {

    public void displayMessageDialog(MessageDialogEvent dialogEvent);
}