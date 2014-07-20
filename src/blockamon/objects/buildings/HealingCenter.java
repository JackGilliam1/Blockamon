package blockamon.objects.buildings;
import blockamon.controllers.menuActions.HealAction;
import blockamon.controllers.menuActions.PlayerAction;
import blockamon.objects.Player;

import java.util.ArrayList;
import java.util.List;

public class HealingCenter extends Building {
	public HealingCenter() {
		super(300, 20, 75, 75, "HealShop", "HealShop");
	}

    public List<PlayerAction> getActions(Player player) {
        List<PlayerAction> actions = new ArrayList<PlayerAction>();
        actions.add(new HealAction(player));
        return actions;
    }

    public void doAction(Player player, String action) {
        if(action.equals("HEAL")) {
        }
	}
}
