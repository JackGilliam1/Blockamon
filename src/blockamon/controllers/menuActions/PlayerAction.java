package blockamon.controllers.menuActions;

import blockamon.objects.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: Jack's Computer
 * Date: 7/12/2014
 * Time: 1:17 PM
 */
public abstract class PlayerAction extends AbstractAction {
    private String _text;
    private String _name;
    protected Player _player;

    public PlayerAction(String text, String name, Player player) {
        _text = text;
        _name = name;
        _player = player;
    }

    public String getText() { return _text; }
    public String getName() { return _name; }
    public abstract void actionPerformed(ActionEvent e);
}
