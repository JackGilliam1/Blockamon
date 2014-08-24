package blockamon.controllers.menuActions;

import blockamon.io.IPrinter;
import blockamon.io.PrintManager;
import blockamon.items.Item;
import blockamon.objects.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: Jack's Computer
 * Date: 6/15/2014
 * Time: 3:02 PM
 */
public class ItemAction extends PlayerAction {
    private Item _item;

    public ItemAction(String actionText, Player player, Item item) {
        super(actionText, item.toString(), player);
        _item = item;
    }

    public void actionPerformed(ActionEvent e) {
        //TODO: Switch messages to display in ui using the IDisplay
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
