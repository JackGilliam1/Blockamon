package blockamon.objects.buildings;

import blockamon.controllers.menuActions.PlayerAction;
import blockamon.objects.Player;
import blockamon.objects.images.ObjectImage;
import blockamon.objects.data.AppearanceData;
import blockamon.objects.data.ImageData;

import javax.swing.*;
import java.util.List;

public abstract class Building extends JComponent {
    private static final String _imagesFolder = "buildings";
    private static ImageData _imageData = new ImageData(_imagesFolder);
    private AppearanceData _appearanceData;
    private ObjectImage _image;
    private String _name;

	public Building(int xPosition, int yPosition, int width, int height, String image, String name) {
        _name = name;
        _appearanceData = new AppearanceData(xPosition, yPosition, width, height);
		this.setBounds(xPosition, yPosition, width, height);
        _imageData = new ImageData(_imagesFolder, image);
		_image = new ObjectImage(new AppearanceData(width, height), _imageData);
		this.add(_image, 0);
		this.repaint();
	}

    public abstract List<PlayerAction> getActions(Player player);

    public AppearanceData getAppearanceData() {
        return _appearanceData;
    }

    public String getName() {
        return _name;
    }
}
