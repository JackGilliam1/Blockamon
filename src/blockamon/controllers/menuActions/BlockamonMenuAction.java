package blockamon.controllers.menuActions;

import blockamon.controllers.WindowDisplay;
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
    private WindowDisplay _windowDisplay;

    public BlockamonMenuAction(Player player, WindowDisplay windowDisplay) {
        super("Blockamon");
        _player = player;
        _windowDisplay = windowDisplay;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _windowDisplay.displayWindow(WindowDisplay.WindowType.BlockamonWindow);
    }
}
