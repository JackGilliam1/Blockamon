package blockamon.controllers.menuActions;

import blockamon.objects.Blockamon;
import blockamon.objects.Player;

import java.awt.event.ActionEvent;

/**
 * User: Jack's Computer
 * Date: 6/15/2014
 * Time: 3:09 PM
 */
public class HealAction extends PlayerAction {
    public HealAction(Player player) {
        super("Heal", "Heal", player);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Healing all blockamon");
        for (Blockamon blockamon : _player.getBlockamon()) {
            blockamon.fullyHeal();
        }
    }
}
