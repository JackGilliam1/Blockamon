package tests.mocks;

import blockamon.objects.data.Direction;
import blockamon.objects.images.IDirectionalObjectImage;

import javax.swing.*;

/**
 * User: Jack's Computer
 * Date: 4/27/2014
 * Time: 2:06 PM
 */
public class MockDirectionalObjectImage extends JComponent implements IDirectionalObjectImage {

    public Direction updateDirection(char key) {
        return Direction.NONE;
    }

    public void updateDirection(Direction direction) {

    }

    public Direction getCurrentDirection() {
        return Direction.NONE;
    }

    public JComponent getComponent() {
        return this;
    }
}
