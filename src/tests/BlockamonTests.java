package tests;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import blockamon.objects.Blockamon;

/**
 * Created with IntelliJ IDEA.
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlockamonTests extends TestCase {

    private Blockamon blockamon;

    public BlockamonTests() {
        blockamon = new Blockamon(20);
    }

    public void testBlockamonHitPointsSubtracts() throws AssertionFailedError {
        takeDamage(10);
        assertEquals(10, blockamon.currentHitPoints());
    }

    public void testBlockamonHitPointsStaysAboveZero() throws AssertionFailedError {
        takeDamage(21);
        assertEquals(0, blockamon.currentHitPoints());
    }

    public void testBlockamonHitPointsAdds() throws Exception {
        takeDamage(10);
        heal(10);
        assertEquals(20, blockamon.currentHitPoints());
    }

    public void testBlockamonHitPointsStayAtOrBelowMaxHitPoints() throws AssertionFailedError {
        takeDamage(5);
        heal(10);
        assertEquals(20, blockamon.currentHitPoints());
    }

    private void takeDamage(int amount) {
        blockamon.takeDamage(amount);
    }

    private void heal(int amount) {
        blockamon.heal(amount);
    }
}
