package blockamon.controllers;

import blockamon.World;
import blockamon.objects.ElementType;
import blockamon.objects.data.Direction;
import blockamon.objects.Player;
import blockamon.objects.Blockamon;

import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BlockamonBattle {

	private Blockamon blockamon, playerBlock;
	private JPanel world;
	private World theWorld;
	private Player player;
	private boolean playerIsFaster;
	private static final int VALUE_NEEDED_TO_FLEE = 4;
	private static final int FLEE_RANGE = 5;
	private static final Random RANDOM_NUMBER = new Random();
	private ControlPanel menu;

	public BlockamonBattle(World w, ControlPanel menu) {
		world = w.getPlayingField();
		theWorld = w;
		this.menu = menu;
		player = w.getPlayer();
	}

    //TODO: Refactor the battle code
	// Sets all of the starting values of the battle
	public void startTheBattle(Blockamon aBlock, Blockamon playersBlockamon) {
		playerBlock = playersBlockamon;
		// determines which block appears
		blockamon = aBlock;
		if (playerBlock != null && blockamon != null) {
			theWorld.changePlayerImage(Direction.RIGHT);
			// notify the world that the player is in battle
			theWorld.changeBattleState();
			menu.storeTheBlockamon(blockamon);
			// adds the opponents blockamon and the two buttons
			addObjects();
			addPlayersBlock(null, playerBlock);
			// tells the user which block appeared
			whichBlockamon(blockamon.elementType());
			// tells the user their health and the opponents health
			printText("Your Blockamons health: " + playerBlock.currentHp()
					+ " Your Foes Blockamons Health " + blockamon.currentHp(), "Everyones current Health", JOptionPane.INFORMATION_MESSAGE, "info");
			// determines whether the player or the blockamon attacks first
			whoGoesFirst();
			// commences the battle
			blockBattle();
			world.repaint();
		} else {
			battleOver();
		}
	}

	private void whoGoesFirst() {
		playerIsFaster = RANDOM_NUMBER.nextBoolean();
	}

	private void addObjects() {
		world.add(blockamon, 0);
		menu.addButtons("battle");
		menu.removeButtons("OutOfBattle");
	}
	public void addPlayersBlock(Blockamon inactiveBlock, Blockamon activeBlock) {
		player.moveBlockamon();
		if(inactiveBlock != null) {
			world.remove(inactiveBlock);
		}
		if(activeBlock != null){
			world.add(activeBlock);
		}
	}
    //TODO: Refactor string such as this
	private void whichBlockamon(ElementType blockAttackType) {
		printText("A wild " + blockAttackType + " Blockamon appears!", "Oh noes!", JOptionPane.WARNING_MESSAGE, "info");
	}

	// does damage to each blockamon
	private void doDamage() {
		// if it is the players turn currently
		if (playerIsFaster) {
			// the opponent takes damage
			blockamon.takeDamage(playerBlock.currentAttack(), playerIsFaster);
		}
		// if it is not the players turn
		else {
			// players blockamon takes damage
			playerTakesDamage();
		}
	}

	private void playerTakesDamage() {
		// the players blockamon takes damage
		playerBlock.takeDamage(blockamon.currentAttack(), playerIsFaster);
	}
	public void printAttackOfBattle() {
		// informs the user of the attack of their blockamon and the enemies
		// blockamon
		printText("Damage that you do: " + playerBlock.currentAttack() + "  "
				+ "Damage your enemy does: " + blockamon.currentAttack(), "Each Blocks Attack", JOptionPane.INFORMATION_MESSAGE, "info");
	}
	public void printStatusOfBattle() {
		// tells the current health for both the player and the
		// opponent
		printText("Your Blockamons health: "
				+ playerBlock.currentHp()
				+ " Your Foes Blockamons Health "
				+ blockamon.currentHp(), "Everyones current Health", JOptionPane.INFORMATION_MESSAGE, "info");
	}
	public void changeActiveBlockamon(Player player) {
		playerBlock = player.getActiveBlockamon();
	}
	public void doAttack(Player player) {
		if (playerBlock != null && blockamon != null) {
			// if the player is ok to do battle
			if (!playerBlock.hasFainted()) {
				doDamage();
				// changes to the opponents turn
				playerIsFaster = !playerIsFaster;
				// if neither the player nor the enemies health is at or below 0
				if (!blockamon.hasFainted() && !playerBlock.hasFainted()) {
					doDamage();
					if (!playerBlock.hasFainted()
							&& !blockamon.hasFainted()) {
						printStatusOfBattle();
					}
				} else {
					// says the battle is over
					battleOver();
					// asks "who is the winner"
					whoWon(player);
				}
			} else {
				if(!player.canFight())
				{
					battleOver();
					whoWon(player);
				}
				else
				{
					printText("Please change your active Blockamon WARNING: Do Not hit the Back Button or you will lose the Battle and White out", "ActiveBlockamon",JOptionPane.WARNING_MESSAGE, "info");
					menu.createPartyButtons(true);
				}
			}
		}
	}
	private void playerWhitedOut() {// tells the user the player whited out
		printText("The player has whited out and was returned to the nearest Healing Center", "Lol you died", JOptionPane.INFORMATION_MESSAGE, "info");
		theWorld.playerWhitedOut();

	}
	private void whoWon(Player player) {
		// if the player has their health go down below 0
		if (!player.canFight()) {
			// the player loses the battle
			printText("You have lost", "Losers Menu", JOptionPane.WARNING_MESSAGE, "info");
			playerWhitedOut();
		}
		// if the players health is not 0 but the opponents is
		else {
			// the player wins the battle
			printText("You have won", "Winners Menu", JOptionPane.PLAIN_MESSAGE, "info");
			playerBlock.gainExperience();
			player.receiveMoney();
		}
	}

	public void battleOver() {
		// removes the attackButton
		menu.removeButtons("battle");
		menu.addButtons("OutofBattle");
		// resets the stats of both blockamon
		blockamon.resetAttack();
		player.resetMyBlocksAttack();
		addPlayersBlock(player.getActiveBlockamon(), null);
		world.remove(blockamon);
		theWorld.changeBattleState();
		theWorld.takeTheFocus();
		world.repaint();
	}

	private void blockBattle() {
		ElementType playerType = playerBlock.elementType();
		ElementType foeType = blockamon.elementType();
        double playerAttackPower = 1;
        double foeAttackPower = 1;
        if(playerType.negatesAttackAgainst(foeType)) {
            foeAttackPower = 0;
        }
        else if(foeType.negatesAttackAgainst(playerType)) {
            playerAttackPower = 0;
        }
        else if(foeType.isWeakAgainst(playerType)) {
            playerAttackPower = 1.5;
            foeAttackPower = .5;
        }
        else if(playerType.isWeakAgainst(foeType)) {
            playerAttackPower = .5;
            foeAttackPower = 1.5;
        }
        adjustAttackPowers(playerAttackPower, foeAttackPower);
        printAttackOfBattle();
	}

	private void adjustAttackPowers(double playerAttackPower, double foeAttackPower) {
		playerBlock.currentAttack(playerAttackPower);
		blockamon.currentAttack(foeAttackPower);
	}

	// player attempts to flee
	public void tryToFlee(Player thePlayer) {
		int fleeValue = RANDOM_NUMBER.nextInt(FLEE_RANGE);
		// if the player is fleeing
		if (!playerBlock.hasFainted()) {
			// if the player is able to escape
			if (fleeValue <= VALUE_NEEDED_TO_FLEE) {
				System.out.println("fled");
				printText("You have fled the battle", "What a Chicken", JOptionPane.INFORMATION_MESSAGE, "info");
				battleOver();
				theWorld.takeTheFocus();
				// if the player is unable to escape
			} else {
				printText("You failed to escape and you took damage", "Lol you Tried fleeing", JOptionPane.WARNING_MESSAGE, "info");
				playerTakesDamage();
				// displays the users health and the opponents health
				printStatusOfBattle();
			}
		} else {
			// tells the user the player whited out
			printText("The player has whited out and was returned to the nearest Healing Center", "Lol you died", JOptionPane.WARNING_MESSAGE, "info");
			whoWon(thePlayer);
			//tells the world that the player has whited out
			theWorld.playerWhitedOut();
			battleOver();
		}
	}

	// prints the text sent in
	private String printText(String text, String title, int typeOfMessage, String methodToUse) {
		String input = null;
		if(methodToUse.equalsIgnoreCase("input")) {
			input = JOptionPane.showInputDialog(null, text, title, typeOfMessage);
		}
		else if(methodToUse.equalsIgnoreCase("info") || methodToUse.equalsIgnoreCase("error"))
		{
			JOptionPane.showMessageDialog(null, text, title, typeOfMessage);
		}
		return input;
	}
}
