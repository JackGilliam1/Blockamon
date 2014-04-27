package blockamon.objects.data;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 12:37 PM
 */
public class AppearanceData {
    private int _width;
    private int _height;
    private int _x;
    private int _y;

    public AppearanceData(int width, int height) {
        this(0, 0, width, height);
    }

    public AppearanceData(int x, int y, int width, int height) {
        _width = width;
        _height = height;
        _x = x;
        _y = y;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public String getPositionString() {
        return getX() + "," + getY();
    }

    public int getX() {
        return _x;
    }

    public void setX(int x) {
        _x = x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int y) {
        _y = y;
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }
}
