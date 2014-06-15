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
public abstract class BuildingAction extends AbstractAction {
    private String _name;
    protected Player _player;

    public BuildingAction(String name, Player player) {
        _name = name;
        _player = player;
    }
    public String getName() {
        return _name;
    }
    public abstract void actionPerformed(ActionEvent e);
}
