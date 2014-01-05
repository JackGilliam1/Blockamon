package tests.objects;

import blockamon.ICriticalHitDeterminator;

/**
 * Created with IntelliJ IDEA.
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlwaysCritical implements ICriticalHitDeterminator {
    public int determineCriticalHit() {
        return 2;
    }
}
