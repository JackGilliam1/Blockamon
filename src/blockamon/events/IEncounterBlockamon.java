package blockamon.events;

import blockamon.objects.Blockamon;

/**
 * User: Jack's Computer
 * Date: 1/19/14
 * Time: 1:54 PM
 */
public interface IEncounterBlockamon extends IEventHandler {
    //Returns whether or not the event was handled
    public boolean encounterBlockamon(IEventArgs eventArgs);

    public class BlockamonEncounterEvent implements IEventArgs {
        private Blockamon _attacker;

        public BlockamonEncounterEvent(Blockamon attacker) {
            _attacker = attacker;
        }

        public Blockamon getAttacker() {
            return _attacker;
        }

        public Object getEventData() {
            return _attacker;
        }
    }
}
