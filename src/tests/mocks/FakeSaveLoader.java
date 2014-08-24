package tests.mocks;

import blockamon.io.ISaveLoader;
import blockamon.objects.Player;

import java.io.IOException;

/**
 * User: Jack's Computer
 * Date: 8/24/2014
 * Time: 9:48 AM
 */
public class FakeSaveLoader implements ISaveLoader {
    private boolean _called;

    public FakeSaveLoader() {
        _called = false;
    }

    public boolean wasCalled() {
        return _called;
    }

    public Player LoadSave() throws IOException {
        _called = true;
        return new Player();
    }

    public Player LoadSave(Player player) throws IOException {
        _called = true;
        return player;
    }
}
