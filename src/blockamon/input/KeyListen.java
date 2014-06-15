package blockamon.input;

import blockamon.World;
import blockamon.controllers.ControlPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class KeyListen implements KeyListener {

	private World world;
    private ControlPanel _controlPanel;
	public KeyListen(World w, ControlPanel controlPanel) {
		world = w;
        _controlPanel = controlPanel;
	}
	public void keyPressed(KeyEvent ke) {
		 char keyPressed = ke.getKeyChar();
		 if(!world.inBattle())
		 {
			 world.movePlayer(keyPressed);
			 world.blockAppear();
			 if(!world.inBattle())
			 {
                 if(world.isInStore()) {
                    _controlPanel.switchToMenu(ControlPanel.MenuType.ItemShop);
                 }
                 else if(world.isInHealingStation()) {
                    _controlPanel.switchToMenu(ControlPanel.MenuType.HealShop);
                 }
                 else {
                     _controlPanel.switchToMenu(ControlPanel.MenuType.OutOfBattle);
                 }
			 }
		 }
		 else
		 {
			 System.out.println("Player cannot move");
		 }
	}
	public void keyReleased(KeyEvent ke) {

	}

	public void keyTyped(KeyEvent ke) {

	}

}
