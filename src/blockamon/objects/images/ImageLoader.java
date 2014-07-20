package blockamon.objects.images;

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

    private Map<String, Image> _loadedImages;

    public ImageLoader() {
        _loadedImages = new HashMap<String, Image>();
    }

    public Image loadImage(ImageData imageData) {
        String imagePath = imageData.getFullFilePath();
        Image loadedImage = findLoadedImage(imagePath);
        if(loadedImage != null) {
            System.out.println("Loaded Cached Image: " + imagePath);
            return loadedImage;
        }

        URL imgURL = getClass().getResource(imagePath);
        if (imgURL != null) {
            Image icon = new ImageIcon(imgURL).getImage();
            System.out.println("Loaded image: " + imagePath);
            _loadedImages.put(imagePath, icon);
            return icon;
        } else {
            System.err.println("Couldn't find file: " + imagePath);
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
