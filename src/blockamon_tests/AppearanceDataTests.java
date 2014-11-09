package blockamon_tests;

import blockamon.objects.data.AppearanceData;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * User: Jack's Computer
 * Date: 6/15/2014
 * Time: 1:56 PM
 */
public class AppearanceDataTests extends TestCase {

    private AppearanceData _appearanceData;
    private AppearanceData _appearanceDataTwo;

    public AppearanceDataTests() {
        _appearanceData = new AppearanceData(50, 50);
        _appearanceDataTwo = new AppearanceData(25, 25);
    }

    public void testAppearanceDataContainsSideOfAnother() {
        _appearanceData.setPosition(0, 0);
        _appearanceDataTwo.setPosition(
                _appearanceData.getX() + _appearanceData.getWidth() - 1,
                _appearanceData.getY());
        boolean contains = _appearanceData.contains(_appearanceDataTwo);

        Assert.assertTrue(contains);
    }
    public void testAppearanceDataContainsTopOfAnother() {
        _appearanceData.setPosition(0, 0);
        _appearanceDataTwo.setPosition(
                _appearanceData.getX(),
                _appearanceData.getY() + _appearanceData.getHeight() - 1);
        boolean contains = _appearanceData.contains(_appearanceDataTwo);

        Assert.assertTrue(contains);
    }

    public void testAppearanceDataContainsNoneOfAnother() {
        _appearanceData.setPosition(0, 0);
        _appearanceDataTwo.setPosition(
                _appearanceData.getX() + _appearanceData.getWidth() + 1,
                _appearanceData.getY() + _appearanceData.getHeight() + 1);

        boolean contains = _appearanceData.contains(_appearanceDataTwo);

        Assert.assertFalse(contains);
    }

    public void testAppearanceDataContainsAllOfAnother() {
        _appearanceData.setPosition(0, 0);
        _appearanceDataTwo.setPosition(0, 0);

        boolean contains = _appearanceData.contains(_appearanceDataTwo);

        Assert.assertTrue(contains);
    }

    public void testAppearanceDataOnSideEdgeOfAnother() {
        _appearanceData.setPosition(0, 0);
        _appearanceDataTwo.setPosition(
                _appearanceData.getX() + _appearanceData.getWidth(),
                _appearanceData.getY());
        boolean contains = _appearanceData.contains(_appearanceDataTwo);

        Assert.assertTrue(contains);
    }

    public void testAppearanceDataOnTopEdgeOfAnother() {
        _appearanceData.setPosition(0, 0);
        _appearanceDataTwo.setPosition(
                _appearanceData.getX(),
                _appearanceData.getY() + _appearanceData.getHeight());
        boolean contains = _appearanceData.contains(_appearanceDataTwo);

        Assert.assertTrue(contains);
    }
}
