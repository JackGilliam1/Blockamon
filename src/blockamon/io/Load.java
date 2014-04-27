package blockamon.io;

import blockamon.items.Item;
import blockamon.objects.ElementType;
import blockamon.objects.Player;
import blockamon.objects.Blockamon;
import generators.BlockamonGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Load {
	private Player player;
    //Formats
    private static final String FILELOADERR_MESSAGEFORMAT = "File %s was nowhere to be found, please place it within the projects folder";
    private static final String FILELOADED_MESSAGE = "You Loaded a Save";
    private static final String FILEPROMPT_MESSAGE = "Type the file you would like to load from the list below with the .save";
    private static final String FILECORRUPTERR_MESSAGE = "File could not be loaded, as it is either corrupt or not in the correct format";

	public Load(Player player) {
		this.player = player;
		loadAFile();
	}

	private void loadAFile() {
        final StringBuilder builder = new StringBuilder();
		Scanner scanner = null;
		File file = null;
		try {
			File filePath = new File("./");
			File[] files = filePath.listFiles();
            builder.append(FILEPROMPT_MESSAGE);
			for(int i = 0; i < files.length; i++)
			{
				if(files[i].isFile())
				{
					String fileName = files[i].getName();
					String[] splitFile = fileName.split(".");
                    if(splitFile.length > 0)
                    {
                        if(splitFile[splitFile.length - 1].contains(".save"))
                        {
                            builder.append("%n" + files[i].getName());
                        }
                    }
				}
			}
			String fileName = JOptionPane.showInputDialog(null, builder.toString(), "What file is it", JOptionPane.QUESTION_MESSAGE);
			if(fileName != null && !fileName.equals(""))
			{
				file = new File(fileName);
				scanner = new Scanner((file));

				scanner.nextLine();
				String Blocks = scanner.nextLine();
				String Items = scanner.nextLine();
				String Money = scanner.nextLine();
				String[] splitMoney = Money.split(" ");
                if(splitMoney.length > 0)
                {
                    double money = Double.parseDouble(splitMoney[1]);
                    player.setMoney(money);
                    String[] splitItems = Items.split(" ");
                    int numOfItems = Integer.parseInt(splitItems[1]);
                    String[] splitBlocks = Blocks.split(" ");
                    int numOfBlocks = Integer.parseInt(splitBlocks[1]);
                    for(int value = 0;value<numOfItems;value++)
                    {
                        getItems(scanner);
                    }
                    scanner.nextLine();
                    for(int value = 0;value<numOfBlocks;value++)
                    {
                        getStats(scanner);
                    }
                    int X = scanner.nextInt();
                    int Y = scanner.nextInt();
                    player.setLocation(X, Y);
                    JOptionPane.showMessageDialog(null, FILELOADED_MESSAGE, "Save has been loaded", JOptionPane.INFORMATION_MESSAGE);
                }
			}
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, String.format(FILELOADERR_MESSAGEFORMAT, file.getName()), "File Not Found", JOptionPane.ERROR_MESSAGE);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null, FILECORRUPTERR_MESSAGE, "File Corrupted", JOptionPane.ERROR_MESSAGE);
		}
        finally {
            if(scanner != null)
                scanner.close();
        }
	}
	private void getItems(Scanner scanner) {
		String ItemName = scanner.nextLine();
		String[] splitItem = ItemName.split(" ");
		int itemNumber = Integer.parseInt(splitItem[1]);
		String item = scanner.nextLine();
		String[] splitSingleItem = item.split(" ");
		String itemName = splitSingleItem[1];
		player.addItem(Enum.valueOf(Item.class, itemName.toUpperCase()));
	}
	private void getStats(Scanner scanner) {
		Blockamon aBlock;
		String blockamon = scanner.nextLine();
		String[] splitBlock = blockamon.split(" ");
		int position = Integer.parseInt(splitBlock[1]);
		String blockamonName = scanner.nextLine();
		int level = Integer.parseInt(scanner.nextLine());
		String blockAttack = scanner.nextLine();
		String blockAttackType = scanner.nextLine();
		String blockHealth = scanner.nextLine();
		String blockIsActive = scanner.nextLine();
		int currentExperience = Integer.parseInt(scanner.nextLine());
		int neededExperience = Integer.parseInt(scanner.nextLine());
		String[] splitName = blockamonName.split(" ");
		String[] splitAttack = blockAttack.split(" ");
		String[] splitAttackType = blockAttackType.split(" ");
		String[] splitHealth = blockHealth.split(" ");
		String[] splitIsActive = blockIsActive.split(" ");
		String name = splitName[1];
		double attack = Double.parseDouble(splitAttack[1]);
		ElementType type = ElementType.valueOf(splitAttackType[1].toUpperCase());
		double health = Double.parseDouble(splitHealth[1]);
		boolean isLead = Boolean.parseBoolean(splitIsActive[1]);
		aBlock = BlockamonGenerator.generateBlockamonOfType(type);
        aBlock.setTotalHealth(health);
        aBlock.setTotalAttack((int) attack);
        aBlock.setName(name);
        aBlock.setCurrentLevel(level);
        aBlock.setExperience(currentExperience);
        aBlock.setNeededExperience(neededExperience);
        aBlock.isLead(isLead);
        player.setBlockamonAt(position, aBlock);
	}
}
