package blockamon.controllers.menuActions;

import blockamon.objects.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: Jack's Computer
 * Date: 8/24/2014
 * Time: 9:57 AM
 */
public class BagAction extends AbstractAction {

    private Player _player;

    public  BagAction(Player player) {
        super("Bag");
        _player = player;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: Write the Bag action event
    }
}
