package blockamon.objects;

import java.awt.*;

/**
 * User: Jack's Computer
 * Date: 1/19/14
 * Time: 1:27 PM
 */
public enum ElementColor {
    BUG(168, 184, 32), DARK(112, 88, 72), DRAGON(112, 56, 248), ELECTRIC(248, 208, 48),
    FAIRY(255, 192, 203), FIGHTING(192, 48, 40), FIRE(240, 128, 48), FLYING(168, 144, 240),
    GHOST(112, 88, 152), GRASS(0, 200, 0), GROUND(227, 199, 106), ICE(152, 216, 216),
    NORMAL(152, 149, 116), POISON(160, 64, 160), PSYCHIC(248, 88, 136), ROCK(184, 160, 56),
    STEEL(184, 184, 208), WATER(0, 0, 200);

    private Color _color;

    private ElementColor(int red, int green, int blue) {
        _color = new Color(red, green, blue);
    }

    public Color getColor() {
        return _color;
    }
}
