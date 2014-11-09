package blockamon.controllers.menuActions;

import blockamon.controllers.WindowDisplay;
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
    private WindowDisplay _windowDisplay;

    public  BagAction(Player player, WindowDisplay windowDisplay) {
        super("Bag");
        _player = player;
        _windowDisplay = windowDisplay;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _windowDisplay.displayWindow(WindowDisplay.WindowType.BagWindow);
    }
}
