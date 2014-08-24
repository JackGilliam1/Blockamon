package tests.objects;
import blockamon.objects.ElementType;
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

    private static final double MaxHp = 20.0;
    private Blockamon blockamon;

    public BlockamonTests() {
        blockamon = new Blockamon(ElementType.NORMAL);
        blockamon.maxHp(MaxHp);
    }

    public void testBlockamonHitPointsSubtracts() throws AssertionFailedError {
        takeDamage(10);
        assertEquals(10.0, blockamon.currentHp());
    }

    public void testBlockamonHitPointsStaysAboveZero() throws AssertionFailedError {
        takeDamage(21);
        assertEquals(0.0, blockamon.currentHp());
    }

    public void testBlockamonHitPointsAdds() throws Exception {
        takeDamage(10);
        heal(10);
        assertEquals(MaxHp, blockamon.currentHp());
    }

    public void testBlockamonHitPointsStayAtOrBelowMaxHitPoints() throws AssertionFailedError {
        takeDamage(5);
        heal(10);
        assertEquals(MaxHp, blockamon.currentHp());
    }

    private void takeDamage(int amount) {
        blockamon.takeDamage(amount);
    }

    private void heal(int amount) {
        blockamon.heal(amount);
    }
}
