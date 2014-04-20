package blockamon.objects.images;

import blockamon.objects.data.ImageData;

import javax.swing.*;
import java.awt.Image;
import java.net.URL;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 1:40 PM
 */
public class ImageLoader implements IImageLoader {
    public Image loadImage(ImageData imageData) {
        String imagePath = imageData.getFullFilePath();
        URL imgURL = getClass().getResource(imagePath);
        if (imgURL != null) {
            Image icon = new ImageIcon(imgURL).getImage();
            System.out.println("Loaded image: " + imagePath);
            return icon;
        } else {
            System.err.println("Couldn't find file: " + imagePath);
        }
        return null;
    }
}
