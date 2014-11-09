package blockamon_tests;

import blockamon.objects.data.AppearanceData;
import blockamon.objects.data.Direction;
import blockamon.objects.data.ImageData;
import blockamon.objects.images.DirectionalObjectImage;
import junit.framework.TestCase;
import blockamon_tests.mocks.MockImageLoader;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 2:05 PM
 */
public class DirectionalObjectImageTests extends TestCase {
    private final int startingX = 5;
    private final int startingY = 10;
    private final int startingWidth = 20;
    private final int startingHeight = 200;
    private final Direction startingDirection = Direction.UP;
    private AppearanceData _appearanceData;
    private DirectionalObjectImage _objectImage;
    private ImageData _imageData;

    public DirectionalObjectImageTests() {
        _appearanceData = new AppearanceData(startingX, startingY, startingWidth, startingHeight);
        _imageData = new ImageData("", startingDirection.name());
        _objectImage = new DirectionalObjectImage(
                _appearanceData,
                _imageData,
                startingDirection,
                new MockImageLoader());
    }

    public void testObjectImageUpdatesDirectionOnCreate() {
        assertEquals(startingDirection, _objectImage.getCurrentDirection());
    }

    public void testObjectImageUpdatesDirectionOnUpdate() {
        Direction updateDirection = Direction.DOWN;

        _objectImage.updateDirection(updateDirection);

        assertEquals(updateDirection, _objectImage.getCurrentDirection());
        assertEquals(updateDirection.name(), _imageData.getImageName());
    }
}
