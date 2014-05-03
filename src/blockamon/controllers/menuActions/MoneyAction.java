package blockamon.controllers.menuActions;

import blockamon.objects.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
* User: Jack's Computer
* Date: 5/3/2014
* Time: 2:25 PM
*/
public class MoneyAction extends AbstractAction {

    private Player _player;

    public MoneyAction(Player player) {
        super("Money");
        _player = player;
    }

    public void actionPerformed(ActionEvent e) {
        double money = _player.getMoney();
        //TODO: Write the Money Action
    }
}
