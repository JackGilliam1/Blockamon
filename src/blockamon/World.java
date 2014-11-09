package blockamon;

import blockamon.controllers.ControlPanel;
import blockamon.input.KeyListen;
import blockamon.io.*;
import blockamon.items.Item;
import blockamon.listeners.IMessageDialogListener;
import blockamon.objects.data.Direction;
import blockamon.objects.Player;
import blockamon.objects.buildings.HealingCenter;
import blockamon.objects.buildings.ItemShop;
import blockamon.objects.encounters.Grass;
import blockamon.generators.BlockamonGenerator;
import blockamon.listeners.MessageDialogEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class World extends JFrame implements IMessageDialogListener {

    private static final String GAME_TITLE = "Blockamon";
	private JPanel playingField;
	private Grass wildGrass;
	private Player player;
	private int counter, counter2;
	private static HealingCenter healingCenter;
    private static ItemShop itemShop;
	private static boolean inBattle;
	private ControlPanel menu;
	private Dimension worldSize;
    private static final String _receivedMessageFormat = "You recieved a(n) %s Blockamon";
    private static final int playerXStartingLocation = 130;
    private static final int playerYStartingLocation = 100;

    private FileFilter _fileFilter;

	public World() {
		super(GAME_TITLE);
        _fileFilter = new FileNameExtensionFilter("Blockamon SAVE", "save");
		this.setLayout(new BorderLayout());
		inBattle = false;
		playingField = new JPanel(null);
        playingField.setBorder(BorderFactory.createLineBorder(Color.black));
		playingField.setPreferredSize(new Dimension(400, 300));
		worldSize = playingField.getPreferredSize();
	}

    public void run() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        player = createPlayer();
        playingField.add(player);
        createItemShop();
        createHealingCenter();
        createGrass();
        menu = new ControlPanel(getPlayer(),
                createSaveWriter(_fileFilter),
                createSaveLoader(_fileFilter),
                getItemShop(),
                getHealingCenter());

        add(menu, BorderLayout.NORTH);
        add(playingField, BorderLayout.CENTER);
        pack();
        player.setVisible(true);
        this.addKeyListener(new KeyListen(this, menu));
        setVisible(true);
        this.repaint();
    }

    private ISaveWriter createSaveWriter(FileFilter fileFilter) {
        return new SaveWriter(new FileChooserHandler(fileFilter));
    }

    private ISaveLoader createSaveLoader(FileFilter fileFilter) {
        return new SaveLoader(
                new FileChooserHandler(fileFilter),
                        new PlayerCreator());
    }







    public ControlPanel getMenu() {
        return menu;
    }

	private void createItemShop() {
		//creates the shop you can buy from
		itemShop = new ItemShop();
        itemShop.stockItems(Item.HEALVIAL, Item.BLOCKABALL);
		playingField.add(itemShop);
	}

	public ItemShop getItemShop() {
		return itemShop;
	}

	//creates grass
	private void createGrass() {
		//creates the grass where wild Blockamon live
		wildGrass = new Grass(0, 0,(int) (worldSize.width * .3), worldSize.height, this, menu);
		playingField.add(wildGrass);
	}
	//creates the player
	private Player createPlayer() {
        IPlayerCreator playerCreator = new PlayerCreator();
        Player player = playerCreator.createNewPlayer();
        generateStartingBlockamon(player);
        player.setLocation(playerXStartingLocation, playerYStartingLocation);
        final String message = String.format(_receivedMessageFormat, player.getLeadBlockamon().elementType());
        displayDialog(message, "Block received", JOptionPane.INFORMATION_MESSAGE);
		return player;
	}

    private void generateStartingBlockamon(Player player) {
        player.setLeadBlockamon(BlockamonGenerator.generateRandomBlockamon());
    }
    //creates the place that can heal
    private void createHealingCenter() {
        healingCenter = new HealingCenter();
        playingField.add(healingCenter);
        playingField.repaint();
    }

	//returns the grass
	public Grass getTheGrass() {
		return wildGrass;
	}
	//returns the healingCenter
	public HealingCenter getHealingCenter() {
		return healingCenter;
	}


	//player loses a battle and all blockamon in their party have lost all HealthPoints
	public void playerWhitedOut() {
		player.setLocation(healingCenter.getX(), healingCenter.getY());
        player.healAllBlockamon();
		player.loseMoney();
		playingField.repaint();
	}
	public boolean isInStore() {
        return itemShop.getAppearanceData().contains(player.getAppearanceData());
	}
	public JPanel getPlayingField() {
		return playingField;
	}
	public boolean isInHealingStation() {
        return healingCenter.getAppearanceData().contains(player.getAppearanceData());
	}
	//returns the player
	public Player getPlayer() {
		return player;
	}


	//change whether the player is in battle or not
	public void changeBattleState() {
		inBattle = !inBattle;
	}
	//is the player in battle
	public boolean inBattle() {
		return inBattle;
	}


	//moves the player
	public void movePlayer(char aKey) {
		//tells the player to move
        System.out.println("Moving player");
		player.movePlayer(aKey, this.getWidth(), this.getHeight());
	}

	public void changePlayerImage(Direction direction) {
		player.changeImage(direction);
	}


	//determines whether the player is within the grass or not
	public void blockAppear() {
		//if the player is within the bounds of the grass
		if(wildGrass.getAppearanceData().contains(player.getAppearanceData()))
		{
			//does a wildBlockamon appear?
		    wildGrass.wildBlockamonAppearance(player.getActiveBlockamon(), player.getX()+ player.getWidth(), player.getY());
		}
	}

	//returns the focus to the window when it is not the focus to begin with
	public void takeTheFocus() {
		this.requestFocusInWindow();
	}



    public void displayMessageDialog(MessageDialogEvent dialogEvent) {
        System.out.println(dialogEvent.getMessageTitle() + " " + dialogEvent.getMessageType());
    }

    /**
     * Displays a Message Dialog box using the specified parameters
     * @param message The message to display
     * @param title The title of the message box
     * @param typeOfMessage The elementType of message being displayed
     */
    public void displayDialog(String message, String title, int typeOfMessage) {
        System.out.println(title + ": " + message);
    }
}
