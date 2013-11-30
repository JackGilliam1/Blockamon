package blockamon.objects;

import blockamon.items.Item;
import generators.BlockamonGenerator;
import listeners.CustomEventHandler;
import listeners.MessageDialogEvent;
import listeners.MessageDialogEventHandler;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Player extends IBoundable {
    private static final String _imagesFolder = "player/";
    
    private CustomEventHandler _eventHandler;
    
	/**
	 * 
	 */
	private static int blockamonX, blockamonY;
    private static Map<GameImage.Direction, GameImage> _images;
    
	private double amountOfMoney = 200;
	private static final int PARTYLIMIT = 6;
	private static final int BAGSIZELIMIT = 6;
	private static final Random randomNumberGenerator = new Random();
    /**
     * Messages
     */
    private static final String PARTYLIMITREACHED_MESSAGE = "You have reached the max limit of Blockamon in your party";
    private static final String BADFULLERR_MESSAGE = "Your bag is full.";
    private static final String LACKFUNDSERR_MESSAGE = "You don't have enough money to buy that item";
    private static final String LACKFUNDSERR_TITLE = "Come back when you get some money, buddy";

    /**
     * FORMATS
     */
    private static final String ITEMRECEIVED_MESSAGEFORMAT = "Player has received a(n) %s";

    private ArrayList<Item> ownedItems;
    private ArrayList<WildBlockamon> ownedBlockamon;
    private Map<Character, GameImage.Direction> controls;

	public Player(int playerWidth, int playerHeight) {
		super();
        _eventHandler = new MessageDialogEventHandler();
        ownedBlockamon = new ArrayList<WildBlockamon>();
		generateStartingBlockamon();
        if(_images == null) {
            _images = new HashMap<GameImage.Direction, GameImage>();
            final int width = playerWidth;
            final int height = playerHeight;
            _images.put(GameImage.Direction.NONE, new GameImage(width, height, _imagesFolder + "PlayerRight.png"));
            _images.put(GameImage.Direction.RIGHT, new GameImage(width, height, _imagesFolder + "PlayerRight.png"));
            _images.put(GameImage.Direction.LEFT, new GameImage(width, height, _imagesFolder + "PlayerLeft.png"));
            _images.put(GameImage.Direction.DOWN, new GameImage(width, height, _imagesFolder + "PlayerDown.png"));
            _images.put(GameImage.Direction.UP, new GameImage(width, height, _imagesFolder + "PlayerUp.png"));
        }
        if(controls == null) {
            controls = new HashMap<Character, GameImage.Direction>();
            controls.put(GameImage.Direction.NONE.getKey(), GameImage.Direction.NONE);
            controls.put(GameImage.Direction.UP.getKey(), GameImage.Direction.UP);
            controls.put(GameImage.Direction.RIGHT.getKey(), GameImage.Direction.RIGHT);
            controls.put(GameImage.Direction.DOWN.getKey(), GameImage.Direction.DOWN);
            controls.put(GameImage.Direction.LEFT.getKey(), GameImage.Direction.LEFT);
        }
        GameImage playerImage = _images.get(GameImage.Direction.LEFT);
        this.setHeight(playerImage.getHeight());
        this.setWidth(playerImage.getWidth());
		this.add(playerImage, 0);
		this.repaint();
        System.out.println("PlayerWidth: " + this.getWidth());
        System.out.println("PlayerHeight: " + this.getHeight());
		//sets the amount of items the player can hold
		ownedItems = new ArrayList<Item>();
        addItem(Item.HEALVIAL);
	}
    
	public void receiveMoney() {
		int money = randomNumberGenerator.nextInt(50)+50;
		amountOfMoney += money;
		JOptionPane.showMessageDialog(null, "You have received " + money + " money", "You are richer", JOptionPane.INFORMATION_MESSAGE);
	}
	public void loseMoney() {
		int randomLoss = randomNumberGenerator.nextInt(20) + 20;
		if(getMoney() - randomLoss > 0)
		{
			JOptionPane.showMessageDialog(null, "You check your pockets an notice you lost $"+ randomLoss + " in your escape", "Money Was Lost", JOptionPane.WARNING_MESSAGE);
			removeMoney(randomLoss);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "You lost no money, because you had no money", "LOL, you are broke", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void resetMyBlocksAttack() {
        for(WildBlockamon blockamon : ownedBlockamon) {
            blockamon.healAttack();
        }
	}
    public void removeMoney(double amount) {
        amountOfMoney -= amount;
        if(amountOfMoney < 0) {
            amountOfMoney = 0;
        }
    }
	
    public void boughtAnItem(Item item){
		if(amountOfMoney - item.getPrice() < 0) {
            displayMessage(new MessageDialogEvent(LACKFUNDSERR_MESSAGE, LACKFUNDSERR_TITLE, JOptionPane.ERROR_MESSAGE));
		}
		else {
			amountOfMoney -= item.getPrice();
			addItem(item);
            displayMessage(new MessageDialogEvent(String.format(ITEMRECEIVED_MESSAGEFORMAT, item.toString()), "Item Received", JOptionPane.INFORMATION_MESSAGE));
		}
	}
	
    
    
	public void moveBlockamon() {
		blockamonX = this.getX() + this.getWidth();
		blockamonY = this.getY() + 20;
        for(final WildBlockamon blockamon : ownedBlockamon) {
            blockamon.setLocation(blockamonX, blockamonY);
        }
	}
	public void healAllBlockamon() {
        for(final WildBlockamon blockamon : ownedBlockamon) {
            blockamon.fullyHeal();
        }
	}
	private void generateStartingBlockamon() {
		//determine which blockamon the player will receive to start
        WildBlockamon leadBlockamon;
        if((leadBlockamon = getLeadBlockamon()) == null)
        {
            leadBlockamon = BlockamonGenerator.generateRandomBlockamon();
            setLeadBlockamon(leadBlockamon);
        }
        leadBlockamon.isLead(true);
	}
    
    
    
    public void addToParty(WildBlockamon capturedBlockamon){
        if(canAddToParty()) {
            ownedBlockamon.add(capturedBlockamon);
        }
        else {
            displayMessage(new MessageDialogEvent(PARTYLIMITREACHED_MESSAGE, "Max Party reached", JOptionPane.WARNING_MESSAGE));
        }
    }
    public boolean canAddToParty() {
        return (getPartySize() + 1) < getPartyLimit();
    }
    
	public void changeImage(GameImage.Direction direction) {
        if(direction != null) {
            Component toRemove;
            if(super.getComponentCount() > 0) {
                if((toRemove = super.getComponent(0)) != null){
                    this.remove(toRemove);
                }
            }
            this.add(_images.get(direction), 0);
        }
	}
	public void movePlayer(char typed, int maxX, int maxY) {
        System.out.println(typed);
		GameImage.Direction direction = controls.get(typed);
        //Position player wants to move to
		int playersNewX = this.getX() + (direction.getModifier() * (direction.isOnXAxis() ? this.getWidth() : 0));
		int playersNewY = this.getY() + (direction.getModifier() * (!direction.isOnXAxis() ? this.getHeight() : 0));
        
        //player clamping, allows the player to loop on the map
        playersNewX = (playersNewX%maxX + maxX)%maxX;
        playersNewY = (playersNewY%maxY + maxY)%maxY;
        
        if(direction != null) {
            changeImage(direction);
            this.setLocation(playersNewX, playersNewY);
            this.repaint();
        }
	}


    public double getMoney() {
        return amountOfMoney;
    }
    public void setMoney(double amount) {
        amountOfMoney = amount;
    }
    public synchronized  int getPartyLimit() {
        return PARTYLIMIT;
    }
    public synchronized  int getPartySize() {
        return ownedBlockamon.size();
    }
	public synchronized  WildBlockamon getBlockamon() {
		//creation of the activeBlockamon is sent to nothing
		WildBlockamon activeBlockamon = null;
		//for the amount of Blockamon a player can hold
		for(int position = 0;position < getPartySize(); position++)
		{
            if(!((activeBlockamon = getBlockamonAt(position)) != null && activeBlockamon.isLead()))
            {
                activeBlockamon = null;
            }
		}
		//return the Blockamon that is active
		return activeBlockamon;
	}
    public synchronized  WildBlockamon getLeadBlockamon() {
        return getBlockamonAt(0);
    }
    public synchronized  void setLeadBlockamon(WildBlockamon blockamon) {
        if(getPartySize() > 0)
        {
            ownedBlockamon.set(0, blockamon);
        }
        else {
            addToParty(blockamon);
        }
    }
    public synchronized  WildBlockamon getBlockamonAt(int index) {
        WildBlockamon blockamon = null;
        if(index < getPartySize())
        {
            blockamon = ownedBlockamon.get(index);
        }
        return blockamon;
    }
    public synchronized  void setBlockamonAt(int index, WildBlockamon blockamon) {
        ownedBlockamon.set(index, blockamon);
    }
    public synchronized  void removeFromParty(int index) {
        ownedBlockamon.remove(index);
    }
    public synchronized  void removeFromParty(WildBlockamon blockamon) {
        ownedBlockamon.remove(blockamon);
    }
    public synchronized void addItem(Item item) {
        if(canAddItem())
        {
            ownedItems.add(item);
        }
        else
        {
            displayMessage(new MessageDialogEvent(BADFULLERR_MESSAGE, "Full Bag", JOptionPane.WARNING_MESSAGE));
        }
    }
    public synchronized  boolean canAddItem() {
        return getBagSize() < getBagLimit();
    }
    public synchronized boolean hasItems() {
        return ownedItems.size() > 0;
    }
    public synchronized  void setItem(int index, Item item) {
        if(getBagSize() > 0)
            ownedItems.set(index, item);
        else
            addItem(item);
    }
    public synchronized  Item getItem(int index) {
        return ownedItems.get(index);
    }
    public synchronized  int getBagSize() {
        return ownedItems.size();
    }
    public synchronized  int getBagLimit() {
        return BAGSIZELIMIT;
    }
    public synchronized  void removeItem(int index) {
        ownedItems.remove(index);
    }
    public synchronized  void removeItem(Item item) {
        ownedItems.remove(item);
    }
    public synchronized  boolean canFight() {
        boolean canFight = false;
        for(WildBlockamon blockamon : ownedBlockamon) {
            if(canFight = !blockamon.hasFainted()) {
                break;
            }
        }
        return canFight;
    }
    public synchronized  void displayMessage(MessageDialogEvent eventObject) {
        _eventHandler._fireEvent(eventObject);
    }
    public synchronized  void addListener(EventListener listener) {
        _eventHandler.addListener(listener);
    }
    public synchronized  void swapPartyAt(int positionOne, int positionTwo) {
        WildBlockamon block = ownedBlockamon.get(positionOne);
        ownedBlockamon.set(positionOne, ownedBlockamon.get(positionTwo));
        ownedBlockamon.set(positionTwo, block);
    }
}
