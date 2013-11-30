package listeners;


import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 1/6/13
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageDialogEvent extends EventObject {

    private String _message;
    private String _messageTitle;
    private int _messageType;

    public MessageDialogEvent(Object source, String message) {
        super(source);
        this._message = message;
    }

    public MessageDialogEvent(Object source, String message, String messageTitle) {
        super(source);
        this._message = message;
        this._messageTitle = messageTitle;
    }

    public MessageDialogEvent(Object source, String message, int messageType) {
        super(source);
        this._message = message;
        this._messageType = messageType;
    }

    public MessageDialogEvent(Object source, String message, String messageTitle, int messageType) {
        super(source);
        this._message = message;
        this._messageTitle = messageTitle;
        this._messageType = messageType;
    }

    public String getMessage() {
        return _message;
    }
    public int getMessageType() {
        return _messageType;
    }
    public String getMessageTitle() {
        return _messageTitle;
    }
}
