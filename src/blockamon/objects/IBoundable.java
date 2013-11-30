package blockamon.objects;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 1/5/13
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class IBoundable extends JComponent {
    
    public void setWidth(int value) {
        this.setSize(value, getHeight());
    }
    public void setHeight(int value) {
        this.setSize(getWidth(), value);
    }
    
    public void setX(int value) {
        this.setLocation(value, getY());
    }
    public void setY(int value) {
        this.setLocation(getX(), value);
    }
    
}
