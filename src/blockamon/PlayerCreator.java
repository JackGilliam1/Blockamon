package blockamon;

import blockamon.objects.Player;

/**
 * User: Jack's Computer
 * Date: 4/27/2014
 * Time: 2:11 PM
 */
public class PlayerCreator implements IPlayerCreator {
    public Player createNewPlayer() {
        return new Player();
    }
}
