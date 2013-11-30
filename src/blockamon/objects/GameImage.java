package blockamon.objects;

import java.awt.*;
import javax.swing.*;

/*
 * 
 */
public class GameImage extends JComponent
{
    private static final String _resourceFolder = "../../resources/";
    public enum Direction {
        UP('w', -1), LEFT('a', -1, true),
        DOWN('s'), RIGHT('d', true),
        NONE(' ');
        
        private char key;
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
            this.key = key;
            this.modifier = modifier;
            this.isOnXAxis = isOnXAxis;
        }
        
        public char getKey() {
            return key;
        }
        
        public void setKey(char key) {
            this.key = key;
        }
        
        public int getModifier() {
            return modifier;
        }
        
        public boolean isOnXAxis() {
            return isOnXAxis;
        }
    }
    
	private Image content;
    public GameImage(int width, int height, String imagePath) {
        this(0, 0, width, height, imagePath);
    }
    
    public GameImage(int xLocation, int yLocation, int width, int height, String imagePath)  {
        super();
        this.setBounds(xLocation, yLocation, width, height);
        java.net.URL imgURL = getClass().getResource(_resourceFolder + imagePath);
        if (imgURL != null) {
            content = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Couldn't find file: " + _resourceFolder + imagePath);
        }
    }
  
    public void paint(Graphics g)  {
        g.drawImage(content, 0, 0, getWidth(), getHeight(), this);
        paintChildren(g);
    }
}
