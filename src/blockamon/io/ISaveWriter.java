package blockamon.io;

import blockamon.objects.Player;

import java.io.IOException;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 12:18 PM
 */
public interface ISaveWriter {
    public void SaveGame(Player player) throws IOException;
}
