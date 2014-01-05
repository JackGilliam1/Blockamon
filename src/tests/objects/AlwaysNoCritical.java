package tests.objects;

import blockamon.ICriticalHitDeterminator;

/**
 * Created with IntelliJ IDEA.
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlwaysNoCritical implements ICriticalHitDeterminator {
    public int determineCriticalHit() {
        return 1;
    }
}
