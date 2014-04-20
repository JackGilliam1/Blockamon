package blockamon.objects.images;

import blockamon.objects.data.AppearanceData;
import blockamon.objects.data.ImageData;

import javax.swing.JComponent;
import java.awt.Image;
import java.awt.Graphics;

public class ObjectImage extends JComponent {
    private AppearanceData _appearanceData;
    private ImageData _imageData;
    private IImageLoader _imageLoader;
    private Image _image;

    public ObjectImage(AppearanceData appearanceData, ImageData imageData) {
        this(appearanceData, imageData, new ImageLoader());
    }

    public ObjectImage(AppearanceData appearanceData, ImageData imageData, IImageLoader imageLoader) {
        super();
        _appearanceData = appearanceData;
        _imageData = imageData;
        this.setBounds(_appearanceData.getX(),
                _appearanceData.getY(),
                _appearanceData.getWidth(),
                _appearanceData.getHeight());
        _imageLoader = imageLoader;
        this.setImage(imageData);
    }

    public void paint(Graphics graphics) {
        graphics.drawImage(_image, 0, 0, _appearanceData.getWidth(), _appearanceData.getHeight(), this);
        super.paintChildren(graphics);
        this.repaint();
    }

    protected ImageData getImageData() {
        return _imageData;
    }

    public void updatePosition(int x, int y) {
        _appearanceData.setX(x);
        _appearanceData.setY(y);
        super.setLocation(x, y);
    }

    protected void setImage(ImageData imageData) {
        _image = _imageLoader.loadImage(imageData);
    }
}
