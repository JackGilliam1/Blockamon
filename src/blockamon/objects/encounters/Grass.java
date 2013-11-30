package blockamon.objects.encounters;

import blockamon.controllers.BlockamonBattle;
import blockamon.controllers.ControlPanel;
import blockamon.World;
import blockamon.objects.Player;
import blockamon.objects.WildBlockamon;
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
//    private static final BlockamonType[] TYPES = BlockamonType.values();
//	private WildBlockamon[] wildBlockamon;
	private static final Random RANDNUM = new Random();
	public Grass(int XPosition, int YPosition, int width, int height, World w, ControlPanel menu)
	{
		super(XPosition, YPosition, width, height);
		this.setBackground(Color.green);
		world = w;
	}
	public void wildBlockamonAppearance(WildBlockamon playerBlock, int playerX, int playerY)
	{
        final double chance = RANDNUM.nextDouble();
        if(chance <= CHANCEOFAPPEARANCE)
        {
            battle = new BlockamonBattle(world, world.getMenu());
            WildBlockamon wildBlockamon = BlockamonGenerator.generateRandomBlockamon();
            wildBlockamon.setX(playerX + DISTANCEXFROMPLAYER);
            wildBlockamon.setY(playerY + DISTANCEYFROMPLAYER);
            battle.startTheBattle(wildBlockamon, playerBlock);
        }
		//position of the wild Blockamon relative to the player
//		playerX += 50;
//		playerY += 20;
			//instantiates all of the Blockamon
//			wildBlockamon[6] = new WildBlockamon(playerX, playerY, 240, 128, 48, "Fire", "Fire", BlockamonType.FIRE);
//			wildBlockamon[7] = new WildBlockamon(playerX, playerY, 104, 144, 240, "Water", "Water", BlockamonType.WATER);
//			wildBlockamon[8] = new WildBlockamon(playerX, playerY, 0, 200, 0, "Grass", "Grass", BlockamonType.GRASS);
//			wildBlockamon[9] = new WildBlockamon(playerX, playerY, 152, 216, 216, "Ice", "Ice", BlockamonType.ICE);
//			wildBlockamon[10] = new WildBlockamon(playerX, playerY, 227, 199, 106, "Ground", "Ground", BlockamonType.GROUND);
//			wildBlockamon[11] = new WildBlockamon(playerX, playerY, 184, 160, 56, "Rock", "Rock", BlockamonType.ROCK);
//			wildBlockamon[12] = new WildBlockamon(playerX, playerY, 248, 208, 48, "Electric", "Electric", BlockamonType.ELECTRIC);
//			wildBlockamon[13] = new WildBlockamon(playerX, playerY, 168, 184, 32, "Bug", "Bug", BlockamonType.BUG);
//			wildBlockamon[14] = new WildBlockamon(playerX, playerY, 112, 88, 72, "Dark", "Dark", BlockamonType.DARK);
//			wildBlockamon[15] = new WildBlockamon(playerX, playerY, 112, 56, 248, "Dragon", "Dragon", BlockamonType.DRAGON);
//			wildBlockamon[16] = new WildBlockamon(playerX, playerY, 192, 48, 40, "Fighting", "Fighter", BlockamonType.FIGHTING);
//			wildBlockamon[17] = new WildBlockamon(playerX, playerY, 168, 144, 240, "Flying", "Flyer", BlockamonType.FLYING);
//			wildBlockamon[18] = new WildBlockamon(playerX, playerY, 112, 88, 152, "Ghost", "Ghost", BlockamonType.GHOST);
//			wildBlockamon[19] = new WildBlockamon(playerX, playerY, 152, 149, 116, "Normal", "Norm", BlockamonType.NORMAL);
//			wildBlockamon[20] = new WildBlockamon(playerX, playerY, 160, 64, 160, "Poison", "Poison", BlockamonType.POISON);
//			wildBlockamon[21] = new WildBlockamon(playerX, playerY, 248, 88, 136, "Psychic", "Psychic", BlockamonType.PSYCHIC);
//			wildBlockamon[22] = new WildBlockamon(playerX, playerY, 184, 184, 208, "Steel", "Steel", BlockamonType.STEEL);
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
	public void addPlayersBlock(WildBlockamon inActiveBlock, WildBlockamon activeBlock)
	{
		battle.addPlayersBlock(inActiveBlock, activeBlock);
	}
}
