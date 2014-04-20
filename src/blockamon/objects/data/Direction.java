package blockamon.objects.data;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 1:36 PM
 */
public enum Direction {
    UP('w', -1), LEFT('a', -1, true),
    DOWN('s'), RIGHT('d', true),
    NONE(' ');

    private char _key;
    private int modifier;
    private boolean isOnXAxis;

    Direction(char key) {
        this(key, false);
    }

    Direction(char key, boolean isOnXAxis) {
        this(key, 1, isOnXAxis);
    }

    Direction(char key, int modifier) {
        this(key, modifier, false);
    }

    Direction(char key, int modifier, boolean isOnXAxis) {
        this._key = key;
        this.modifier = modifier;
        this.isOnXAxis = isOnXAxis;
    }

    public char key() {
        return _key;
    }

    public void key(char key) {
        _key = key;
    }

    public int getModifier() {
        return modifier;
    }

    public boolean isOnXAxis() {
        return isOnXAxis;
    }

    public boolean isKey(char key) {
        return _key == key;
    }
}
