package blockamon;

import blockamon.controllers.ControlPanel;
import blockamon.input.KeyListen;
import blockamon.items.Item;
import blockamon.objects.GameImage;
import blockamon.objects.Player;
import blockamon.objects.buildings.HealingCenter;
import blockamon.objects.buildings.ItemShop;
import blockamon.objects.encounters.Grass;
import listeners.MessageDialogEvent;
import listeners.MessageDialogListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;


public class World extends JFrame implements MessageDialogListener {

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
    private static final int playerWidth = 30;
    private static final int playerHeight = 50;
    
    
    
	public World() {
		super(GAME_TITLE);
		this.setLayout(new BorderLayout());
		inBattle = false;
		playingField = new JPanel(null);
		playingField.setPreferredSize(new Dimension(400, 300));
		worldSize = playingField.getPreferredSize();
		playingField.setBorder(BorderFactory.createLineBorder(Color.black));
	}
    
    public void run() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        player = createPlayer();
        playingField.add(player);
        player.addListener(this);
        createItemShop();
        createHealingCenter();
        createGrass();
        menu = new ControlPanel(getPlayer(), getItemShop(), getHealingCenter(), getTheGrass());
        menu.addButtons("OutofBattle");
        add(menu, BorderLayout.NORTH);
        add(playingField, BorderLayout.CENTER);
        pack();
        player.setVisible(true);
        setVisible(true);
        this.repaint();
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
		Player player = new Player(playerWidth, playerHeight);
        player.setLocation(playerXStartingLocation, playerYStartingLocation);
        final String message = String.format(_receivedMessageFormat, player.getLeadBlockamon().getElementType());
        displayDialog(message, "Block received", JOptionPane.INFORMATION_MESSAGE);
		this.addKeyListener(new KeyListen(this));
		return player;
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
	public void inStore() {
		//if the player is within the bounds of the healing center
		if(player.getX() < itemShop.getX()+itemShop.getWidth()&& 
		player.getX()+ player.getWidth() > itemShop.getX()&&
		player.getY() < itemShop.getY()+itemShop.getHeight()&&
		player.getY() + player.getHeight() > itemShop.getY())
		{
			counter2 = 0;
			//add the buttons to heal
			menu.removeButtons("Store");
			menu.addButtons("ItemShop");
			menu.removeButtons("OutOfBattle");
			menu.removeButtons("OOBInfoBackButton");
			menu.removeButtons("Info");
		}
		else
		{
				//remove the buttons to heal
				menu.removeButtons("ItemShop");
				menu.removeButtons("Store");
				if(counter2 == 0)
				{
					menu.addButtons("OutofBattle");
				}
				counter2++;
		}
	}
	public JPanel getPlayingField() {
		return playingField;
	}
	public void inHealingStation() {
		//if the player is within the bounds of the healing center
		if(player.getX() < healingCenter.getX()+ healingCenter.getWidth()&& 
		player.getX()+ player.getWidth() > healingCenter.getX()&&
		player.getY() < healingCenter.getY()+ healingCenter.getHeight()&&
		player.getY() + player.getHeight() > healingCenter.getY())
		{
			counter = 0;
			//add the buttons to heal
			menu.addButtons("HealCenter");
			menu.removeButtons("OutOfBattle");
			menu.removeButtons("OOBinfoBackButton");
			menu.removeButtons("Info");
		}
		else
		{
			//remove the buttons to heal
				menu.removeButtons("HealCenter");
				if(counter == 0)
				{
					menu.addButtons("OutOfBattle");
					counter++;
				}
		}
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
		menu.removeButtons("info");
		menu.removeButtons("OOBinfoBackButton");
		//tells the player to move
		player.movePlayer(aKey, this.getWidth(), this.getHeight());
	}
    
	public void changePlayerImage(GameImage.Direction direction) {
		player.changeImage(direction);
	}
	
	
	//determines whether the player is within the grass or not
	public void blockAppear() {
		//if the player is within the bounds of the grass
		if(player.getX() < wildGrass.getX()+wildGrass.getWidth()&& 
		   player.getX()+ player.getWidth() > wildGrass.getX()&&
		   player.getY() < wildGrass.getY()+wildGrass.getHeight()&&
		   player.getY() + player.getHeight() > wildGrass.getY())
		{
			//does a wildBlockamon appear?
		    wildGrass.wildBlockamonAppearance(player.getBlockamon(), player.getX()+ player.getWidth(), player.getY());
		}
		//if the player is outside the bounds of the grass
		else
		{
			
		}
	}
	
	//returns the focus to the window when it is not the focus to begin with
	public void takeTheFocus() {
		this.requestFocusInWindow();
	}

    
    
    public void displayMessageDialog(MessageDialogEvent dialogEvent) {
        JOptionPane.showMessageDialog(null, dialogEvent.getMessage(), dialogEvent.getMessageTitle(), dialogEvent.getMessageType());
    }

    /**
     * Displays a Message Dialog box using the specified parameters
     * @param message The message to display
     * @param title The title of the message box
     * @param typeOfMessage The type of message being displayed
     */
    public void displayDialog(String message, String title, int typeOfMessage) {
        JOptionPane.showMessageDialog(null, message, title, typeOfMessage);
    }
}
