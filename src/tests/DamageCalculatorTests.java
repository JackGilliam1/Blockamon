package tests;

import blockamon.DamageCalculator;
import blockamon.objects.ElementType;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import tests.objects.AlwaysCritical;
import tests.objects.AlwaysNoCritical;

/**
 * Created with IntelliJ IDEA.
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
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
}
