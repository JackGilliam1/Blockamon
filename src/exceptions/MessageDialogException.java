package exceptions;


/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 1/6/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageDialogException extends Exception
{
    private String _messageTitle;
    private int _messageType;
    
    public MessageDialogException(String message) {
        super(message);
    }
    
    public MessageDialogException(String message, String messageTitle) {
        super(message);
        this._messageTitle = messageTitle;
    }
    
    public MessageDialogException(String message, int messageType) {
        super(message);
        this._messageType = messageType;
    }
    
    public MessageDialogException(String message, String messageTitle, int messageType) {
        super(message);
        this._messageTitle = messageTitle;
        this._messageType = messageType;
    }
    
    public int getMessageType() {
        return _messageType;
    }
    public String getMessageTitle() {
        return _messageTitle;
    }
}
