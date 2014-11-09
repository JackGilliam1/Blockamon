package blockamon_tests;


import blockamon.objects.data.AppearanceData;
import blockamon.objects.data.ImageData;
import blockamon.objects.images.ObjectImage;
import junit.framework.TestCase;
import blockamon_tests.mocks.MockImageLoader;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 1:38 PM
 */
public class ObjectImageTests extends TestCase {

    private final int startingX = 5;
    private final int startingY = 10;
    private final int startingWidth = 20;
    private final int startingHeight = 200;
    private AppearanceData _appearanceData;
    private ObjectImage _objectImage;

    public ObjectImageTests() {
        _appearanceData = new AppearanceData(startingX, startingY, startingWidth, startingHeight);
        _objectImage = new ObjectImage(
                _appearanceData,
                new ImageData(""),
                new MockImageLoader());
    }

    public void testObjectImageSetsStartingComponentPosition() {
        assertEquals(startingX, _objectImage.getX());
        assertEquals(startingY, _objectImage.getY());
    }

    public void testObjectImageUpdatesComponentPosition() {
        int updateX = 80;
        int updateY = 800;

        _objectImage.updatePosition(updateX, updateY);

        assertEquals(updateX, _objectImage.getX());
        assertEquals(updateY, _objectImage.getY());
    }

    public void testObjectImageUpdatesComponentWidth() {
        assertEquals(startingWidth, _objectImage.getWidth());
        assertEquals(startingHeight, _objectImage.getHeight());
    }

    public void testObjectImageUpdatesAppearanceDataPosition() {
        int updateX = 80;
        int updateY = 800;

        _objectImage.updatePosition(updateX, updateY);

        assertEquals(updateX, _appearanceData.getX());
        assertEquals(updateY, _appearanceData.getY());
    }
}
