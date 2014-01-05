package blockamon;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomCriticalHitDeterminator implements ICriticalHitDeterminator {
    private static final double _criticalHitThreshold = 0.005;
    private static Random criticalHitGenerator = new Random();

    public int determineCriticalHit() {
        int criticalHitModifier = 1;
        double criticalHitValue = criticalHitGenerator.nextDouble();
        if(criticalHitValue < _criticalHitThreshold) {
            criticalHitModifier = 2;
        }
        return criticalHitModifier;
    }
}
