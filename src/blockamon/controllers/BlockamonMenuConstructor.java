package blockamon.controllers;

import blockamon.controllers.menuActions.*;
import blockamon.io.ISaveLoader;
import blockamon.io.ISaveWriter;
import blockamon.items.Item;
import blockamon.objects.Blockamon;
import blockamon.objects.Player;
import blockamon.objects.buildings.Building;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack's Computer
 * Date: 7/12/2014
 * Time: 12:26 PM
 */
public class BlockamonMenuConstructor {
    private Player _player;
    private ISaveWriter _saveWriter;
    private ISaveLoader _saveLoader;
    private WindowDisplay _windowDisplay;

    public BlockamonMenuConstructor(Player player,
                                    ISaveWriter saveWriter,
                                    ISaveLoader saveLoader,
                                    WindowDisplay windowDisplay) {
        _player = player;
        _saveWriter = saveWriter;
        _saveLoader = saveLoader;
        _windowDisplay = windowDisplay;
    }

    public JMenu createBuildingMenu(Building building, Player player) {
        JMenu menu = new JMenu(building.getName());
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
        JMenuItem blockamonMenuItem = new JMenuItem("Blockamon");
        blockamonMenuItem.setName("Blockamon");
        blockamonMenuItem.setAction(new BlockamonMenuAction(_player, _windowDisplay));
        menu.add(blockamonMenuItem);

        JMenuItem bagMenuItem = new JMenuItem("Bag");
        bagMenuItem.setName("Bag");
        bagMenuItem.setAction(new BagAction(_player));
        menu.add(bagMenuItem);

        JMenuItem moneyMenuItem = new JMenuItem("Money");
        moneyMenuItem.setName("Money");
        moneyMenuItem.setAction(new MoneyAction(_player, _windowDisplay));
        menu.add(moneyMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("Save");
        saveMenuItem.setAction(new SaveAction(_player, _saveWriter));
        menu.add(saveMenuItem);

        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.setName("Load");
        loadMenuItem.setAction(new LoadAction(_player, _saveLoader));
        menu.add(loadMenuItem);
        return menu;
    }

    public JMenu createItemMenu() {
        Item[] items = _player.getItems();
        int numberOfItems = items.length;
        JMenu menu = new JMenu("Items");
        menu.setName("Items");

        for(int i = 0; i < numberOfItems; i++) {
            Item item = items[i];
            JMenuItem menuItem = new JMenuItem(item.getName());
            menuItem.setName(item.name());
            menuItem.setAction(new ItemAction(item.getName(), _player, item));
            menu.add(menuItem);
        }

        addBackMenuItem(menu);

        return menu;
    }

    public JMenu createBlockamonMenu() {
        JMenu menu = new JMenu("Blockamon");
        menu.setName("Blockamon");

        ArrayList<Blockamon> blockamons = _player.getBlockamon();
        Blockamon blockamon;
        for(int i = 0; i < blockamons.size(); i++) {
            blockamon = blockamons.get(i);
            String text = blockamon.name() + ", " + blockamon.level() + ", " + blockamon.currentHp() + "/" + blockamon.maxHp();
            JMenuItem menuItem = new JMenuItem(text);
            menuItem.setName(blockamon.name() + "" + i);
            menuItem.setAction(new BlockamonAction(text));
            menu.add(menuItem);
        }

        addBackMenuItem(menu);

        return menu;
    }

    private void addBackMenuItem(JMenu menu) {
        JMenuItem backMenuItem = new JMenuItem("Back");
        backMenuItem.setName("Back");
        backMenuItem.setAction(new BackAction());
        menu.add(backMenuItem);
    }
}
