package blockamon.objects;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 1/5/13
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class IColorable
{
    protected Color color;
    
    public IColorable(int red, int green, int blue) {
        color = new Color(red, green, blue);
    }
}
