package blockamon.io;

import blockamon.objects.Player;

import java.io.IOException;

/**
 * User: Jack's Computer
 * Date: 4/27/2014
 * Time: 1:47 PM
 */
public interface ISaveLoader {
    public Player LoadSave() throws IOException;
}
