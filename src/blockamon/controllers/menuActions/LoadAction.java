package blockamon.controllers.menuActions;

import blockamon.io.ISaveLoader;
import blockamon.objects.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
* User: Jack's Computer
* Date: 5/3/2014
* Time: 2:24 PM
*/
public class LoadAction extends AbstractAction {
    private ISaveLoader _saveLoader;
    private Player _player;

    public LoadAction(Player player, ISaveLoader saveLoader) {
        super("Load");
        _player = player;
        _saveLoader = saveLoader;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            _saveLoader.LoadSave(_player);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
