package blockamon.controllers.menuActions;

import blockamon.objects.Player;
import blockamon.objects.buildings.Building;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
* User: Jack's Computer
* Date: 5/3/2014
* Time: 2:25 PM
*/
public class BuildingAction extends AbstractAction {
    private Player _player;
    private Building _building;

    public BuildingAction(Player player, Building building, String actionCommand) {
        super(actionCommand);
        _player = player;
        _building = building;
    }

    public void actionPerformed(ActionEvent e) {
        _building.doAction(_player, e.getActionCommand());
    }
}
