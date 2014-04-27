package blockamon.objects.images;

import blockamon.objects.data.Direction;

import javax.swing.*;

/**
 * User: Jack's Computer
 * Date: 4/27/2014
 * Time: 2:01 PM
 */
public interface IDirectionalObjectImage {
    public Direction updateDirection(char key);
    public void updateDirection(Direction direction);
    public Direction getCurrentDirection();
    public JComponent getComponent();
}
