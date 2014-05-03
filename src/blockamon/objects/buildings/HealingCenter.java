package blockamon.objects.buildings;
import blockamon.input.ActionObject;
import blockamon.objects.Blockamon;
import blockamon.objects.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class HealingCenter extends Building {
	public HealingCenter() {
		super(300, 20, 75, 75, "HealShop");
	}

    public List<String> getActions() {
        List<String> actions = new ArrayList<String>();
        actions.add("HEAL");
        return actions;
    }

    public void doAction(Player player, ActionObject action) {
        if(action.isHealAction()) {
            List<Blockamon> blockamons = player.getBlockamon();
            for (Blockamon blockamon : blockamons) {
                blockamon.fullyHeal();
            }
            JOptionPane.showMessageDialog(null,
                    "Your blockamon are now healed", "Blocks healed", JOptionPane.INFORMATION_MESSAGE);
        }
	}
}
