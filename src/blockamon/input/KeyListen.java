package blockamon.input;

import blockamon.World;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class KeyListen implements KeyListener {

	private World world;
	public KeyListen(World w) {
		world = w;
	}
	public void keyPressed(KeyEvent ke) {
		 char keyPressed = ke.getKeyChar();
		 if(!world.inBattle())
		 {
			 world.movePlayer(keyPressed);
			 world.blockAppear();
			 if(!world.inBattle())
			 {
				 world.inStore();
				 world.inHealingStation();
			 }
		 }
		 else
		 {
			 JOptionPane.showMessageDialog(null, "Player cannot move", "Can't Move", JOptionPane.WARNING_MESSAGE);
		 }
	}
	public void keyReleased(KeyEvent ke) {
		
	}

	public void keyTyped(KeyEvent ke) {
		
	}

}
