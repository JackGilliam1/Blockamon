package blockamon.events;

/**
 * User: Jack's Computer
 * Date: 1/19/14
 * Time: 2:01 PM
 */
public interface IEventHandler {
    public void handle(IEventArgs args);

    public boolean canHandle(IEventArgs args);
}
