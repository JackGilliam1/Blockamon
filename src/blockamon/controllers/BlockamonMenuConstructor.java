package blockamon.controllers;

import blockamon.controllers.menuActions.PlayerAction;
import blockamon.controllers.menuActions.SaveAction;
import blockamon.io.ISaveWriter;
import blockamon.objects.Player;
import blockamon.objects.buildings.Building;

import javax.swing.*;
import java.util.List;

/**
 * User: Jack's Computer
 * Date: 7/12/2014
 * Time: 12:26 PM
 */
public class BlockamonMenuConstructor {
    private Player _player;
    private ISaveWriter _saveWriter;

    public BlockamonMenuConstructor(Player player, ISaveWriter saveWriter) {
        _player = player;
        _saveWriter = saveWriter;
    }

    public JMenu createBuildingMenu(Building building, Player player) {
        JMenu menu = new JMenu();
        menu.setName(building.getName());
        List<PlayerAction> actions = building.getActions(player);
        for(PlayerAction action : actions) {
            JMenuItem menuItem = new JMenuItem(action);
            menuItem.setName(action.getName());
            menuItem.setText(action.getText());
            menu.add(menuItem);
        }
        return menu;
    }

    public JMenu createBattleMenu() {
        JMenu menu = new JMenu("Actions");
        menu.setName("Battle");
        JMenuItem menuItemOne = new JMenuItem("Attack");
        menuItemOne.setName("Attack");
        menu.add(menuItemOne);
        JMenuItem menuItemTwo = new JMenuItem("Bag");
        menuItemTwo.setName("Bag");
        menu.add(menuItemTwo);
        JMenuItem menuItemThree = new JMenuItem("Blockamon");
        menuItemThree.setName("Blockamon");
        menu.add(menuItemThree);
        JMenuItem menuItemFour = new JMenuItem("Flee");
        menuItemFour.setName("Flee");
        menu.add(menuItemFour);
        return menu;
    }

    public JMenu createOutOfBattleMenu() {
        JMenu menu = new JMenu("Actions");
        menu.setName("OutOfBattle");
        JMenuItem menuItemOne = new JMenuItem("Blockamon");
        menuItemOne.setName("Blockamon");
        menu.add(menuItemOne);
        JMenuItem menuItemTwo = new JMenuItem("Bag");
        menuItemTwo.setName("Bag");
        menu.add(menuItemTwo);
        JMenuItem menuItemThree = new JMenuItem("Money");
        menuItemThree.setName("Money");
        menu.add(menuItemThree);

        JMenuItem menuItemFour = new JMenuItem("Save");
        menuItemFour.setName("Save");
        menuItemFour.setAction(new SaveAction(_player, _saveWriter));
        menu.add(menuItemFour);
        JMenuItem menuItemFive = new JMenuItem("Load");
        menuItemFive.setName("Load");
        menu.add(menuItemFive);
        return menu;
    }
}
