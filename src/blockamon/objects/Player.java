package blockamon.objects;

import blockamon.items.Item;
import blockamon.objects.data.AppearanceData;
import blockamon.objects.data.Direction;
import blockamon.objects.data.ImageData;
import blockamon.objects.images.DirectionalObjectImage;

import javax.swing.*;
import java.util.*;

public class Player extends IBoundable {
    private static final String _imagesFolder = "player";

    private AppearanceData _appearanceData;
    private DirectionalObjectImage _objectImage;
    private final int WIDTH = 30;
    private final int HEIGHT = 50;


	private static int blockamonX, blockamonY;

	private double amountOfMoney = 200;
	private static final int PARTYLIMIT = 6;
	private static final int BAGSPACE = 6;
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

    private Map<Item, Integer> _itemsMap;

    private ArrayList<Blockamon> _blockamon;

	public Player() {
        _appearanceData = new AppearanceData(WIDTH, HEIGHT);
        _objectImage = new DirectionalObjectImage(
                _appearanceData,
                new ImageData(_imagesFolder));
        _itemsMap = new HashMap<Item, Integer>();

        this.paintPlayer();
        this.add(_objectImage, 0);

        _blockamon = new ArrayList<Blockamon>();

        System.out.println("PlayerWidth: " + this.getWidth());
        System.out.println("PlayerHeight: " + this.getHeight());
	}

    public void changeImage(Direction direction) {
        if(direction != null) {
//            Component componentToRemove;
//            if(super.getComponentCount() > 0) {
//                if((componentToRemove = super.getComponent(0)) != null){
//                    this.remove(componentToRemove);
//                }
//            }
            //this.add(_images.get(direction), 0);
            _objectImage.updateDirection(direction);

        }
    }
    public AppearanceData getPosition() {
        return _appearanceData;
    }
    public void setPosition(int x, int y) {
        _appearanceData.setPosition(x, y);
        this.setLocation(x, y);
        this.repaint();
    }

    public void movePlayer(char typed, int maxX, int maxY) {
        System.out.println(typed);
        Direction direction = _objectImage.updateDirection(typed);
        //Position player wants to move to
        int playersNewX = this.getX() + (direction.getModifier() * (direction.isOnXAxis() ? this.getWidth() : 0));
        int playersNewY = this.getY() + (direction.getModifier() * (!direction.isOnXAxis() ? this.getHeight() : 0));

        //player clamping, allows the player to loop on the map
        playersNewX = (playersNewX%maxX + maxX)%maxX;
        playersNewY = (playersNewY%maxY + maxY)%maxY;

        if(direction != null) {
            changeImage(direction);
            this.setPosition(playersNewX, playersNewY);
        }
    }

    private void paintPlayer() {
        this.setHeight(_objectImage.getHeight());
        this.setWidth(_objectImage.getWidth());
        this.add(_objectImage, 0);
        this.repaint();
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
        for(Blockamon blockamon : _blockamon) {
            blockamon.healAttack();
        }
	}
    public void removeMoney(double amount) {
        amountOfMoney -= amount;
        if(amountOfMoney < 0) {
            amountOfMoney = 0;
        }
    }



	public void moveBlockamon() {
		blockamonX = this.getX() + this.getWidth();
		blockamonY = this.getY() + 20;
        for(final Blockamon blockamon : _blockamon) {
            blockamon.setLocation(blockamonX, blockamonY);
        }
	}
	public void healAllBlockamon() {
        for(final Blockamon blockamon : _blockamon) {
            blockamon.fullyHeal();
        }
	}

    public void addToParty(Blockamon blockamon){
        if(canAddToParty()) {
            _blockamon.add(blockamon);
        }
    }
    public boolean canAddToParty() {
        return getPartySize() < getPartyLimit();
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
        return _blockamon.size();
    }
    public synchronized ArrayList<Blockamon> getBlockamon() {
        return _blockamon;
    }
	public synchronized Blockamon getActiveBlockamon() {
		//creation of the activeBlockamon is sent to nothing
		Blockamon activeBlockamon = null;
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
    public synchronized Blockamon getLeadBlockamon() {
        return getBlockamonAt(0);
    }
    public synchronized void setLeadBlockamon(Blockamon blockamon) {
        if(canAddToParty()) {
            for(Blockamon blockamonInParty : getBlockamon()) {
                blockamonInParty.isLead(false);
            }
            blockamon.isLead(true);
            _blockamon.add(0, blockamon);
        }
    }
    public synchronized Blockamon getBlockamonAt(int index) {
        Blockamon blockamon = null;
        if(index < getPartySize())
        {
            blockamon = _blockamon.get(index);
        }
        return blockamon;
    }
    public synchronized  void setBlockamonAt(int index, Blockamon blockamon) {
        _blockamon.set(index, blockamon);
    }
    public synchronized  void removeFromParty(int index) {
        _blockamon.remove(index);
    }
    public synchronized  void removeFromParty(Blockamon blockamon) {
        _blockamon.remove(blockamon);
    }
    public synchronized void addItem(Item item) {
        if(canAddItem()) {
            if(_itemsMap.containsKey(item)) {
                int count = _itemsMap.get(item) + 1;
                _itemsMap.remove(item);
                _itemsMap.put(item, count);
            }
            else {
                _itemsMap.put(item, 1);
            }
        }
    }
    public synchronized int getItemCount(Item item) {
        if(_itemsMap.containsKey(item)) {
            return _itemsMap.get(item);
        }
        return 0;
    }
    public synchronized  boolean canAddItem() {
        return getBagSpaceUsed() < getBagSpace();
    }
    public synchronized Item[] getItems() {
        int current = 0;
        Item[] items = new Item[_itemsMap.size()];
        for(Item item : Item.values()) {
            if(current < items.length && _itemsMap.containsKey(item)) {
                items[current++] = item;
            }
        }
        return items;
    }
    public synchronized  int getBagSpaceUsed() {
        int bagSpaceUsed = 0;
        for(Item item : Item.values()) {
            if(_itemsMap.containsKey(item)) {
                bagSpaceUsed += _itemsMap.get(item);
            }
        }
        return bagSpaceUsed;
    }
    public synchronized  int getBagSpace() {
        return BAGSPACE;
    }
    public synchronized  void removeItem(Item item) {
        if(_itemsMap.containsKey(item)) {
            int count = _itemsMap.get(item) - 1;
            _itemsMap.remove(item);
            if(count > 0) {
                _itemsMap.put(item, count);
            }
        }
    }
    public synchronized  boolean canFight() {
        boolean canFight = false;
        for(Blockamon blockamon : _blockamon) {
            if(canFight = !blockamon.hasFainted()) {
                break;
            }
        }
        return canFight;
    }
    public synchronized  void swapPartyAt(int positionOne, int positionTwo) {
        Blockamon block = _blockamon.get(positionOne);
        _blockamon.set(positionOne, _blockamon.get(positionTwo));
        _blockamon.set(positionTwo, block);
    }
}
