package blockamon.objects.images;

import blockamon.objects.data.AppearanceData;
import blockamon.objects.data.Direction;
import blockamon.objects.data.ImageData;

import java.util.ArrayList;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 12:57 PM
 */
public class DirectionalObjectImage extends ObjectImage {
    private Direction _currentDirection;
    private ArrayList<Direction> _directions;

    public DirectionalObjectImage(AppearanceData appearanceData, ImageData imageData) {
        this(appearanceData, imageData, Direction.NONE, new ImageLoader());
    }

    public DirectionalObjectImage(AppearanceData appearanceData, ImageData imageData, Direction startingDirection, IImageLoader imageLoader) {
        super(appearanceData, imageData, imageLoader);
        updateDirection(startingDirection);
        setUpDirections();
    }

    public Direction updateDirection(char key) {
        Direction direction = Direction.NONE;
        for(Direction direction1 : _directions) {
            if(direction1.isKey(key)) {
                direction = direction1;
                break;
            }
        }
        this.updateDirection(direction);
        return direction;
    }

    public void updateDirection(Direction direction) {
        ImageData imageData = super.getImageData();
        imageData.setImageName(direction.toString());
        _currentDirection = direction;
        super.setImage(imageData);
    }

    public Direction getCurrentDirection() {
        return _currentDirection;
    }

    private void setUpDirections() {
        _directions = new ArrayList<Direction>();
        _directions.add(Direction.UP);
        _directions.add(Direction.DOWN);
        _directions.add(Direction.LEFT);
        _directions.add(Direction.RIGHT);
    }
}
