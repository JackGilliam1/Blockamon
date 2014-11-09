package blockamon_tests.mocks;

import blockamon.io.ISaveWriter;
import blockamon.objects.Player;

import java.io.IOException;

/**
 * User: Jack's Computer
 * Date: 7/20/2014
 * Time: 12:10 PM
 */
public class FakeSaveWriter implements ISaveWriter {
    private boolean _called;

    public FakeSaveWriter() {
        _called = false;
    }

    public boolean wasCalled() {
        return _called;
    }

    public void SaveGame(Player player) throws IOException {
        _called = true;
    }
}
