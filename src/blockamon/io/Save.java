package blockamon.io;

import blockamon.IncorrectNameException;
import blockamon.items.Item;
import blockamon.objects.Blockamon;
import blockamon.objects.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class Save {

	private Player player;
    private static final String PLAYERSTATSFORMAT = "Player Stats:%nNumberOfBlocks %d%nNumberOfItems %d%nAmountOfMoney %d %n";
    private static final String ITEMFORMAT = "Item %d%nAnItem %s%n";
    private static final String BLOCKAMONSTATSFORMAT = "%nBlockamon %d%nName %s%n%d%nAttack %s%nAttackType %s%nHealth %s%nisActive %s%n%d%n%d";
    private static final String PLAYERPOSITIONFORMAT = "%n%d%n%d";
    private static final String FILESAVED_MESSAGEFORMAT = "File has been saved as %s";
    private static final String FILECREATED_MESSAGEFORMAT = "File %s was not found so a Save was created";
    private static final String INCORRECTFILENAME_MESSAGE = "File name is incorrect it cannot be null or empty";
	public Save(Player player) {
		super();
		this.player = player;
		File file = null;
		try {
			file = new File(JOptionPane.showInputDialog(null, "What would you like to name this save?", "Name the save", JOptionPane.QUESTION_MESSAGE) + ".save");
			if(!file.getName().contains("null"))
			{
				PrintWriter saveFile = new PrintWriter(file);
				savePlayersProgress(saveFile);
				JOptionPane.showMessageDialog(null, String.format(FILESAVED_MESSAGEFORMAT, file.getName()), "Game is Saved", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				throw new IncorrectNameException();
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, String.format(FILECREATED_MESSAGEFORMAT, file.getName()), "No file found", JOptionPane.ERROR_MESSAGE);
		} catch (IncorrectNameException e)
		{
			JOptionPane.showMessageDialog(null, INCORRECTFILENAME_MESSAGE, "Incorrect File Name", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void savePlayersProgress(PrintWriter save)
	{
		int amountOfPlayersBlockamon = player.getPartySize();
		double amountOfMoneyPlayerHas = 0;
		int amountOfItemsPlayerHas = player.getBagSize();
		save.printf(PLAYERSTATSFORMAT, amountOfPlayersBlockamon, amountOfItemsPlayerHas, amountOfMoneyPlayerHas);

		for(int position = 0; position < amountOfItemsPlayerHas; position++)
		{
			Item item = player.getItem(position);
			if(item != null)
			{
				save.printf(ITEMFORMAT, position, item.toString());
			}
		}
		for(int position = 0; position < amountOfPlayersBlockamon; position++)
		{
			Blockamon blockamon = player.getBlockamonAt(position);
			if(blockamon != null)
			{
				save.printf(BLOCKAMONSTATSFORMAT, position, blockamon.getName(), blockamon.getCurrentLevel(),
						blockamon.getCurrentAttack(), blockamon.getElementType(), blockamon.getTotalHealth(), blockamon.isLead(), blockamon.getExperience(), blockamon.getNeededExperience());
			}
		}
		save.printf(PLAYERPOSITIONFORMAT, player.getX(), player.getY());
		save.close();
	}
}
