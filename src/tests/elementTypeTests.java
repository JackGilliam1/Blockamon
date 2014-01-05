package tests;

import blockamon.objects.ElementType;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElementTypeTests extends TestCase {
    public void testWaterIsWeakAgainstGrass() throws AssertionFailedError {
        boolean result = ElementType.WATER.isWeakAgainst(ElementType.GRASS);
        assertEquals(true, result);
    }

    public void testGrassIsWeakAgainstWater() throws AssertionFailedError {
        boolean result = ElementType.GRASS.isWeakAgainst(ElementType.WATER);
        assertEquals(false, result);
    }

    public void testGrassResistsWater() throws AssertionFailedError {
        boolean result = ElementType.GRASS.resists(ElementType.WATER);
        assertEquals(true, result);
    }

    public void testWaterResistsGrass() throws AssertionFailedError {
        boolean result = ElementType.WATER.resists(ElementType.GRASS);
        assertEquals(false, result);
    }

    public void testGhostNegatesFighting() throws AssertionFailedError {
        boolean result = ElementType.GHOST.negates(ElementType.FIGHTING);
        assertEquals(true, result);
    }

    public void testFightingNegatesGhost() throws AssertionFailedError {
        boolean result = ElementType.FIGHTING.negates(ElementType.GHOST);
        assertEquals(false, result);
    }
}
