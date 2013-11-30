package blockamon.objects.buildings;

import blockamon.items.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemShop extends Building {
    private static List<Item> _items;
	public ItemShop() {
		super(300, 150, 75, 75, "ItemShop.png");
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
	public Item getSpecificItem(int index) {
        return _items.get(index);
	}
	public int getNumberOfItems() {
        return _items.size();
	}
}
