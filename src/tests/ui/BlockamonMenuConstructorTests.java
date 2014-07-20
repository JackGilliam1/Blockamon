package tests.ui;

import blockamon.controllers.BlockamonMenuConstructor;
import blockamon.controllers.menuActions.*;
import blockamon.io.ISaveWriter;
import blockamon.items.Item;
import blockamon.objects.Player;
import blockamon.objects.buildings.Building;
import blockamon.objects.buildings.HealingCenter;
import blockamon.objects.buildings.ItemShop;
import junit.framework.TestCase;
import org.junit.Assert;
import tests.mocks.FakeSaveWriter;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack's Computer
 * Date: 7/12/2014
 * Time: 12:25 PM
 */
public class BlockamonMenuConstructorTests extends TestCase {
    private Player _player;
    private BlockamonMenuConstructor _constructor;
    private FakeSaveWriter _saveWriter;

    public BlockamonMenuConstructorTests() {
        _player = new Player();
        _saveWriter = new FakeSaveWriter();
        _constructor = new BlockamonMenuConstructor(_player, _saveWriter);
    }

    public void testCreatesTheItemShopMenu() {
        ItemShop building = new ItemShop();
        building.stockItems(Item.BLOCKABALL, Item.HEALVIAL);
        JMenu menu = _constructor.createBuildingMenu(building, null);

        Assert.assertNotNull(menu);
        Assert.assertEquals(building.getName(), menu.getName());
        Assert.assertEquals(2, menu.getItemCount());
        for(int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem menuItem = menu.getItem(i);
            Action action = menuItem.getAction();
            Assert.assertNotNull(action);
            Assert.assertEquals(ItemAction.class, action.getClass());

            String name = menuItem.getName();
            if(name.equals(Item.BLOCKABALL.toString())) {
                Assert.assertEquals(Item.BLOCKABALL.getWellFormattedString(), menuItem.getText());
            }
            else if(name.equals(Item.HEALVIAL.toString())) {
                Assert.assertEquals(Item.HEALVIAL.getWellFormattedString(), menuItem.getText());
            }
            else {
                Assert.fail("Name: " + name + " Text: " + menuItem.getText());
            }
        }
    }

    public void testCreatesTheHealingCenterMenu() {
        Building building = new HealingCenter();
        JMenu menu = _constructor.createBuildingMenu(building, null);

        Assert.assertNotNull(menu);
        Assert.assertEquals(building.getName(), menu.getName());
        Assert.assertEquals(1, menu.getItemCount());
        for(int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem menuItem = menu.getItem(i);
            Action action = menuItem.getAction();
            Assert.assertNotNull(action);
            Assert.assertEquals(HealAction.class, action.getClass());

            Assert.assertEquals("Heal", menuItem.getName());
            Assert.assertEquals("Heal", menuItem.getText());
        }
    }

    public void testCreatesBattleMenu() {
        JMenu menu = _constructor.createBattleMenu();

        assertNameAndText(menu, "Battle", "Actions");

        Assert.assertEquals(4, menu.getItemCount());

        assertNameAndText(menu.getItem(0), "Attack", "Attack");

        assertNameAndText(menu.getItem(1), "Bag", "Bag");

        assertNameAndText(menu.getItem(2), "Blockamon", "Blockamon");

        assertNameAndText(menu.getItem(3), "Flee", "Flee");
    }

    public void testCreatesOutOfBattleMenu() {
        JMenu menu = _constructor.createOutOfBattleMenu();

        assertNameAndText(menu, "OutOfBattle", "Actions");

        Assert.assertEquals(5, menu.getItemCount());

        assertNameAndText(menu.getItem(0), "Blockamon", "Blockamon");
        assertNameAndText(menu.getItem(1), "Bag", "Bag");
        assertNameAndText(menu.getItem(2), "Money", "Money");
        JMenuItem saveMenuItem = menu.getItem(3);
        Action saveMenuAction = saveMenuItem.getAction();
        Assert.assertNotNull(saveMenuAction);
        Assert.assertEquals(saveMenuAction.getClass(), SaveAction.class);
        assertNameAndText(saveMenuItem, "Save", "Save");
        assertNameAndText(menu.getItem(4), "Load", "Load");
    }

    private void assertNameAndText(JMenu menu, String name, String text) {
        Assert.assertEquals("Name of the Menu should be " + name, name, menu.getName());
        Assert.assertEquals("Text of the Menu should be " + text, text, menu.getText());
    }

    private void assertNameAndText(JMenuItem menuItem, String name, String text) {
        Assert.assertEquals("Name of menu item is " + name, name, menuItem.getName());
        Assert.assertEquals("Text of menu item is " + text, text, menuItem.getText());
    }

}
