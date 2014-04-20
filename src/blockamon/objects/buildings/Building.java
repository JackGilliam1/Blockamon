package blockamon.objects.buildings;
import blockamon.objects.images.ObjectImage;
import blockamon.items.Item;
import blockamon.objects.data.AppearanceData;
import blockamon.objects.data.ImageData;

import javax.swing.*;

public class Building extends JComponent {

    private static final String _imagesFolder = "buildings";
    private static ImageData _imageData = new ImageData(_imagesFolder);
    private ObjectImage _image;

	public Building(int xPosition, int yPosition, int width, int height, String image) {
		super();
		this.setBounds(xPosition, yPosition, width, height);
		_image = new ObjectImage(new AppearanceData(width, height), new ImageData(_imagesFolder, image));
		this.add(_image, 0);
		this.repaint();
	}

	public Item getSpecificItem(int position) {
		return null;
	}

	public int getNumberOfItems() {
		return 0;
	}
}
