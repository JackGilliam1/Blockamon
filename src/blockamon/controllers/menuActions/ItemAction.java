package blockamon.controllers.menuActions;

import blockamon.items.Item;
import blockamon.objects.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: Jack's Computer
 * Date: 6/15/2014
 * Time: 3:02 PM
 */
public class ItemAction extends BuildingAction {
    private Item _item;

    public ItemAction(String name, Player player, Item item) {
        super(name, player);
        _item = item;
    }

    public void actionPerformed(ActionEvent e) {
        if(_player.getMoney() >= _item.getPrice()) {
            System.out.println("Adding item to player: " + getName());
            _player.addItem(_item);
            _player.removeMoney(_item.getPrice());
        }
        else {
            System.out.println(
                    "Not enough money on player: item price: " + _item.getPrice() + " player money: " + _player.getMoney());
        }
    }
}
