package blockamon.controllers.menuActions;

import blockamon.objects.Player;

import java.awt.event.ActionEvent;

/**
 * User: Jack's Computer
 * Date: 7/12/2014
 * Time: 1:27 PM
 */
public class SwitchAction extends PlayerAction {

    public SwitchAction(Player player) {
        super("Switch", "Switch", player);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
