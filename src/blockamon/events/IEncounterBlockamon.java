package blockamon.events;

import blockamon.objects.Blockamon;

/**
 * User: Jack's Computer
 * Date: 1/19/14
 * Time: 1:54 PM
 */
public interface IEncounterBlockamon {
    //Returns whether or not the event was handled
    public boolean encounterBlockamon(BlockamonEncounterEvent event);
}
