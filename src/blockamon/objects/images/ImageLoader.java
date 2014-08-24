package blockamon.objects.images;

import blockamon.io.IPrinter;
import blockamon.io.PrintManager;
import blockamon.objects.data.ImageData;

import javax.swing.*;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 1:40 PM
 */
public class ImageLoader implements IImageLoader {

    private IPrinter _printer = PrintManager.getPrinter(this);
    private Map<String, Image> _loadedImages;

    public ImageLoader() {
        _loadedImages = new HashMap<String, Image>();
    }

    public Image loadImage(ImageData imageData) {
        String imagePath = imageData.getFullFilePath();
        Image loadedImage = findLoadedImage(imagePath);
        if(loadedImage != null) {
            _printer.out("Loaded Cached Image: " + imagePath);
            return loadedImage;
        }

        URL imgURL = getClass().getResource(imagePath);
        if (imgURL != null) {
            Image icon = new ImageIcon(imgURL).getImage();
            _printer.out("Loaded image: " + imagePath);
            _loadedImages.put(imagePath, icon);
            return icon;
        } else {
            _printer.err("Couldn't find file: " + imagePath);
        }
        return null;
    }

    public Image findLoadedImage(String imagePath) {
        if(_loadedImages.containsKey(imagePath)) {
            return _loadedImages.get(imagePath);
        }
        return null;
    }
}
