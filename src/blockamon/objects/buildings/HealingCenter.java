package blockamon.objects.buildings;
import blockamon.controllers.menuActions.BuildingAction;
import blockamon.controllers.menuActions.HealAction;
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

    public List<BuildingAction> getActions(Player player) {
        List<BuildingAction> actions = new ArrayList<BuildingAction>();
        actions.add(new HealAction(player));
        return actions;
    }

    public void doAction(Player player, String action) {
        if(action.equals("HEAL")) {
        }
	}
}
