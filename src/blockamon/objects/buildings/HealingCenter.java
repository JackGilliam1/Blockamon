package blockamon.objects.buildings;
import blockamon.World;
import blockamon.objects.Player;
import blockamon.objects.buildings.Building;

import javax.swing.*;

public class HealingCenter extends Building {
	private World world;
	private Player thePlayer;
	public HealingCenter(Player aPlayer, World w) {
		super(300, 20, 75, 75, "HealShop");
		world = w;
		thePlayer = aPlayer;
	}
	//heals the players Blockamon
	public void healTheBlockamon() {
		thePlayer.healAllBlockamon();
		JOptionPane.showMessageDialog(null, "Your blockamon are now healed", "Blocks healed", JOptionPane.INFORMATION_MESSAGE);
		world.takeTheFocus();
	}
}
