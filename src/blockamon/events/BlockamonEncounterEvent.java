package blockamon.events;

import blockamon.objects.Blockamon;

/**
 * User: Jack's Computer
 * Date: 1/20/14
 * Time: 12:44 PM
 */
public class BlockamonEncounterEvent {
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
