package blockamon.objects;

import blockamon.items.Item;
import blockamon.objects.data.AppearanceData;
import blockamon.objects.data.Direction;
import blockamon.objects.data.ImageData;
import blockamon.objects.images.DirectionalObjectImage;
import blockamon.objects.images.ObjectImage;
import generators.BlockamonGenerator;

import javax.swing.*;
import java.awt.*;
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


    private ArrayList<Item> _items;
    private ArrayList<Blockamon> _blockamon;

	public Player() {
        _appearanceData = new AppearanceData(WIDTH, HEIGHT);
        _objectImage = new DirectionalObjectImage(
                _appearanceData,
                new ImageData(_imagesFolder));

        this.paintPlayer();
        this.add(_objectImage, 0);

        _blockamon = new ArrayList<Blockamon>();
        _items = new ArrayList<Item>();

        System.out.println("PlayerWidth: " + this.getWidth());
        System.out.println("PlayerHeight: " + this.getHeight());

        addItem(Item.HEALVIAL);
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
    public void setPosition(int x, int y) {
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



    public void addToParty(Blockamon capturedBlockamon){
        if(canAddToParty()) {
            _blockamon.add(capturedBlockamon);
        }
        else {
        }
    }
    public boolean canAddToParty() {
        return (getPartySize() + 1) < getPartyLimit();
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
	public synchronized Blockamon getBlockamon() {
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
    public synchronized  void setLeadBlockamon(Blockamon blockamon) {
        if(getPartySize() > 0)
        {
            _blockamon.set(0, blockamon);
        }
        else {
            addToParty(blockamon);
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
        if(canAddItem())
        {
            _items.add(item);
        }
        else
        {
        }
    }
    public synchronized  boolean canAddItem() {
        return getBagSize() < getBagLimit();
    }
    public synchronized boolean hasItems() {
        return _items.size() > 0;
    }
    public synchronized  void setItem(int index, Item item) {
        if(getBagSize() > 0)
            _items.set(index, item);
        else
            addItem(item);
    }
    public synchronized  Item getItem(int index) {
        return _items.get(index);
    }
    public synchronized  int getBagSize() {
        return _items.size();
    }
    public synchronized  int getBagLimit() {
        return BAGSIZELIMIT;
    }
    public synchronized  void removeItem(int index) {
        _items.remove(index);
    }
    public synchronized  void removeItem(Item item) {
        _items.remove(item);
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
