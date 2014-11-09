package blockamon_tests;

import blockamon.DamageCalculator;
import blockamon.ICriticalHitDeterminator;
import blockamon.objects.ElementType;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 3:59 PM
 */
public class DamageCalculatorTests extends TestCase {

    private DamageCalculator calculator;

    public DamageCalculatorTests() {
        calculator = new DamageCalculator(new AlwaysNoCritical());
    }

    public void testCalculatesForResistance() throws AssertionFailedError {
        int resultingDamage = calculator.calculate(20, ElementType.WATER, ElementType.GRASS);
        assertEquals(10, resultingDamage);
    }

    public void testCalculatesForWeakness() throws AssertionFailedError {
        int resultingDamage = calculator.calculate(20, ElementType.GROUND, ElementType.ELECTRIC);
        assertEquals(40, resultingDamage);
    }

    public void testCalculatesForNegation() throws AssertionFailedError {
        int resultingDamage = calculator.calculate(20, ElementType.GROUND, ElementType.FLYING);
        assertEquals(0, resultingDamage);
    }

    public void testCalculatesForCriticalHitWithResistance() throws AssertionFailedError {
        calculator = new DamageCalculator(new AlwaysCritical());
        int resultingDamage = calculator.calculate(20, ElementType.WATER, ElementType.GRASS);
        assertEquals(20, resultingDamage);
    }

    public void testCalculatesForCriticalHitWithWeakness() throws AssertionFailedError {
        calculator = new DamageCalculator(new AlwaysCritical());
        int resultingDamage = calculator.calculate(20, ElementType.GROUND, ElementType.ELECTRIC);
        assertEquals(80, resultingDamage);
    }

    public void testCalculatesForCriticalHitWithNegation() throws AssertionFailedError {
        calculator = new DamageCalculator(new AlwaysCritical());
        int resultingDamage = calculator.calculate(20, ElementType.GROUND, ElementType.FLYING);
        assertEquals(0, resultingDamage);
    }

    private class AlwaysCritical implements ICriticalHitDeterminator {
        public int determineCriticalHit() {
            return 2;
        }
    }

    private class AlwaysNoCritical implements ICriticalHitDeterminator {
        public int determineCriticalHit() {
            return 1;
        }
    }
}
