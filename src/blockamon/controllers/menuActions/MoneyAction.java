package blockamon.controllers.menuActions;

import blockamon.controllers.WindowDisplay;
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
    private WindowDisplay _windowDisplay;

    public MoneyAction(Player player, WindowDisplay windowDisplay) {
        super("Money");
        _player = player;
        _windowDisplay = windowDisplay;
    }

    public void actionPerformed(ActionEvent e) {
        _windowDisplay.displayWindow(WindowDisplay.WindowType.Money);
    }
}
