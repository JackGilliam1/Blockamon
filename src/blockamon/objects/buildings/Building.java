package blockamon.objects.buildings;
import blockamon.objects.GameImage;
import blockamon.items.Item;

import javax.swing.*;

public class Building extends JComponent {

    private static final String _imagesFolder = "buildings/";
    
	public Building(int xPosition, int yPosition, int width, int height, String image)
	{
		super();
		this.setBounds(xPosition, yPosition, width, height);
		GameImage buildingPicture = new GameImage(width, height, _imagesFolder + image);
		this.add(buildingPicture, 0);
		this.repaint();
	}

	public Item getSpecificItem(int position) {
		return null;
	}

	public int getNumberOfItems() {
		return 0;
	}
}
