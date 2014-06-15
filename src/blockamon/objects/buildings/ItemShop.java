package blockamon.objects.buildings;

import blockamon.controllers.menuActions.BuildingAction;
import blockamon.controllers.menuActions.ItemAction;
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

    public List<BuildingAction> getActions(Player player) {
        List<BuildingAction> actions = new ArrayList<BuildingAction>();
        for(Item item : Item.values()) {
            actions.add(new ItemAction(item.getName() + ", " + item.getPrice(), player, item));
        }
        return actions;
    }

    public void doAction(Player player, String action) {
        action = action.toLowerCase();
        for(Item item : _items) {
            String itemName = item.getName();
            if(itemName.toLowerCase().equals(action)) {
                System.out.println("Adding item to player: " + itemName);
                player.addItem(item);
            }
        }
    }

    public Item getSpecificItem(int index) {
        return _items.get(index);
	}
	public int getNumberOfItems() {
        return _items.size();
	}
}
