package blockamon.objects.buildings;

import blockamon.input.ActionObject;
import blockamon.items.Item;
import blockamon.objects.Player;

import java.util.ArrayList;
import java.util.List;

public class ItemShop extends Building {
    private static List<Item> _items;
	public ItemShop() {
		super(300, 150, 75, 75, "ItemShop");
        if(_items == null) {
            _items = new ArrayList<Item>();
        }
	}
    public void stockItem(Item item) {
        if(!_items.contains(item)) {
            _items.add(item);
        }
    }
    public void stockItems(Item... items) {
        for(final Item item : items) {
            stockItem(item);
        }
    }

    public List<String> getActions() {
        List<String> actions = new ArrayList<String>();
        for(Item item : Item.values()) {
            actions.add(item.toString());
        }
        return actions;
    }

    public void doAction(Player player, ActionObject action) {
        if(action.isItemAction()) {
            player.addItem(action.getItem());
        }
    }

    public Item getSpecificItem(int index) {
        return _items.get(index);
	}
	public int getNumberOfItems() {
        return _items.size();
	}
}
