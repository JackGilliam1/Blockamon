package tests.mocks;

import blockamon.IPlayerCreator;
import blockamon.objects.Player;

/**
 * User: Jack's Computer
 * Date: 4/27/2014
 * Time: 2:10 PM
 */
public class MockPlayerCreator implements IPlayerCreator {
    public Player createNewPlayer() {
        return new Player(new MockDirectionalObjectImage());
    }
}
