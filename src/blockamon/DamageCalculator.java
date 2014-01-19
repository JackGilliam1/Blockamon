package blockamon;

import blockamon.objects.ElementType;

/**
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 4:00 PM
 */
public class DamageCalculator {

    private ICriticalHitDeterminator _criticalHitGenerator;

    public DamageCalculator(ICriticalHitDeterminator criticalHitGenerator) {
        _criticalHitGenerator = criticalHitGenerator;
    }

    public int calculate(int damage, ElementType attacker, ElementType defender) {
        double modifier;
        if(defender.isWeakAgainst(attacker)) {
            modifier = 2;
        }
        else if(defender.resists(attacker)) {
            modifier = 0.5;
        }
        else if(defender.negates(attacker)) {
            modifier = 0;
        }
        else {
            modifier = 1;
        }
        return (int)(damage * modifier * _criticalHitGenerator.determineCriticalHit());
    }
}
