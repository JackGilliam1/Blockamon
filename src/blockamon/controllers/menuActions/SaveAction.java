package blockamon.controllers.menuActions;

import blockamon.io.ISaveWriter;
import blockamon.objects.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
* User: Jack's Computer
* Date: 5/3/2014
* Time: 2:24 PM
*/
public class SaveAction extends AbstractAction {
    private ISaveWriter _saveWriter;
    private Player _player;

    public SaveAction(Player player, ISaveWriter saveWriter) {
        super("Save");
        _player = player;
        _saveWriter = saveWriter;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            _saveWriter.SaveGame(_player);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
