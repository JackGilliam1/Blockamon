package blockamon.objects.images;

import blockamon.objects.data.ImageData;

import java.awt.Image;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 1:40 PM
 */
public interface IImageLoader {
    public Image loadImage(ImageData imageData);
}
