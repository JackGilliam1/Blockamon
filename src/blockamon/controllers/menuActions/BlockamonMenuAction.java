package blockamon.controllers.menuActions;

import blockamon.objects.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: Jack's Computer
 * Date: 8/24/2014
 * Time: 10:01 AM
 */
public class BlockamonMenuAction extends AbstractAction {

    private Player _player;

    public BlockamonMenuAction(Player player) {
        super("Blockamon");
        _player = player;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: Write the Blockamon action event handler
    }
}
