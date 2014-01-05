package blockamon.objects.encounters;

import blockamon.controllers.BlockamonBattle;
import blockamon.controllers.ControlPanel;
import blockamon.World;
import blockamon.objects.Blockamon;
import blockamon.objects.Player;
import generators.BlockamonGenerator;

import java.awt.*;
import java.util.*;

public class Grass extends shapes.Rectangle {

	/**
	 *
	 */
	private static final long serialVersionUID = 2967832841209310540L;
	private World world;
	private BlockamonBattle battle;
    private static final int DISTANCEXFROMPLAYER = 50;
    private static final int DISTANCEYFROMPLAYER = 20;
    private static final double CHANCEOFAPPEARANCE = 0.2;
//    private static final ElementType[] TYPES = ElementType.values();
//	private Blockamon[] wildBlockamon;
	private static final Random RANDNUM = new Random();
	public Grass(int XPosition, int YPosition, int width, int height, World w, ControlPanel menu)
	{
		super(XPosition, YPosition, width, height);
		this.setBackground(Color.green);
		world = w;
	}
	public void wildBlockamonAppearance(Blockamon playerBlock, int playerX, int playerY)
	{
        final double chance = RANDNUM.nextDouble();
        if(chance <= CHANCEOFAPPEARANCE)
        {
            battle = new BlockamonBattle(world, world.getMenu());
            Blockamon blockamon = BlockamonGenerator.generateRandomBlockamon();
            blockamon.setX(playerX + DISTANCEXFROMPLAYER);
            blockamon.setY(playerY + DISTANCEYFROMPLAYER);
            battle.startTheBattle(blockamon, playerBlock);
        }
		//position of the wild Blockamon relative to the player
//		playerX += 50;
//		playerY += 20;
			//instantiates all of the Blockamon
//			wildBlockamon[6] = new Blockamon(playerX, playerY, 240, 128, 48, "Fire", "Fire", ElementType.FIRE);
//			wildBlockamon[7] = new Blockamon(playerX, playerY, 104, 144, 240, "Water", "Water", ElementType.WATER);
//			wildBlockamon[8] = new Blockamon(playerX, playerY, 0, 200, 0, "Grass", "Grass", ElementType.GRASS);
//			wildBlockamon[9] = new Blockamon(playerX, playerY, 152, 216, 216, "Ice", "Ice", ElementType.ICE);
//			wildBlockamon[10] = new Blockamon(playerX, playerY, 227, 199, 106, "Ground", "Ground", ElementType.GROUND);
//			wildBlockamon[11] = new Blockamon(playerX, playerY, 184, 160, 56, "Rock", "Rock", ElementType.ROCK);
//			wildBlockamon[12] = new Blockamon(playerX, playerY, 248, 208, 48, "Electric", "Electric", ElementType.ELECTRIC);
//			wildBlockamon[13] = new Blockamon(playerX, playerY, 168, 184, 32, "Bug", "Bug", ElementType.BUG);
//			wildBlockamon[14] = new Blockamon(playerX, playerY, 112, 88, 72, "Dark", "Dark", ElementType.DARK);
//			wildBlockamon[15] = new Blockamon(playerX, playerY, 112, 56, 248, "Dragon", "Dragon", ElementType.DRAGON);
//			wildBlockamon[16] = new Blockamon(playerX, playerY, 192, 48, 40, "Fighting", "Fighter", ElementType.FIGHTING);
//			wildBlockamon[17] = new Blockamon(playerX, playerY, 168, 144, 240, "Flying", "Flyer", ElementType.FLYING);
//			wildBlockamon[18] = new Blockamon(playerX, playerY, 112, 88, 152, "Ghost", "Ghost", ElementType.GHOST);
//			wildBlockamon[19] = new Blockamon(playerX, playerY, 152, 149, 116, "Normal", "Norm", ElementType.NORMAL);
//			wildBlockamon[20] = new Blockamon(playerX, playerY, 160, 64, 160, "Poison", "Poison", ElementType.POISON);
//			wildBlockamon[21] = new Blockamon(playerX, playerY, 248, 88, 136, "Psychic", "Psychic", ElementType.PSYCHIC);
//			wildBlockamon[22] = new Blockamon(playerX, playerY, 184, 184, 208, "Steel", "Steel", ElementType.STEEL);
			//if the selected position has a blockamon in it
//			if(wildBlockamon[randChance] != null)
//			{
//				//start the battle
//				battle.startTheBattle(wildBlockamon[randChance], playerBlock);
//			}

	}
	public void printStats(Player aPlayer)
	{
		battle.changeActiveBlockamon(aPlayer);
		battle.printAttackOfBattle();
		battle.printStatusOfBattle();
	}
	public void battleOver()
	{
		battle.battleOver();
	}
	public void doAttack(Player player) {
		battle.doAttack(player);
	}
	//player attempts to flee
	public void tryToFlee(Player aPlayer)
	{
		battle.tryToFlee(aPlayer);
	}
	public void addPlayersBlock(Blockamon inActiveBlock, Blockamon activeBlock)
	{
		battle.addPlayersBlock(inActiveBlock, activeBlock);
	}
}
