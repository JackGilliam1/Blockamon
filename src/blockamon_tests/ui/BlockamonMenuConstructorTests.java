package blockamon_tests.ui;

import blockamon.controllers.BlockamonMenuConstructor;
import blockamon.controllers.WindowDisplay;
import blockamon.controllers.menuActions.*;
import blockamon.io.Printer;
import blockamon.items.Item;
import blockamon.objects.Blockamon;
import blockamon.objects.ElementType;
import blockamon.objects.Player;
import blockamon.objects.buildings.Building;
import blockamon.objects.buildings.HealingCenter;
import blockamon.objects.buildings.ItemShop;
import junit.framework.TestCase;
import org.junit.Assert;
import blockamon_tests.mocks.FakeSaveLoader;
import blockamon_tests.mocks.FakeSaveWriter;

import javax.swing.*;

/**
 * User: Jack's Computer
 * Date: 7/12/2014
 * Time: 12:25 PM
 */
public class BlockamonMenuConstructorTests extends TestCase {
    private Player _player;
    private BlockamonMenuConstructor _constructor;
    private FakeSaveWriter _saveWriter;
    private FakeSaveLoader _saveLoader;

    public BlockamonMenuConstructorTests() {
        Printer.EnableLogging = false;
        _player = new Player();
        _saveWriter = new FakeSaveWriter();
        _saveLoader = new FakeSaveLoader();
        _constructor = new BlockamonMenuConstructor(_player, _saveWriter, _saveLoader, new WindowDisplay());
    }

    public void testCreatesTheItemShopMenu() {
        ItemShop building = new ItemShop();
        building.stockItems(Item.BLOCKABALL, Item.HEALVIAL);
        JMenu menu = _constructor.createBuildingMenu(building, null);

        Assert.assertNotNull(menu);
        Assert.assertEquals(building.getName(), menu.getName());
        Assert.assertEquals(building.getName(), menu.getText());
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
        Assert.assertEquals(building.getName(), menu.getText());
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

    public void testSaveActionIsCorrect() {
        JMenu menu = _constructor.createOutOfBattleMenu();

        JMenuItem saveMenuItem = getWithName(menu, "Save");
        assertNotNull("Save menu item was not added", saveMenuItem);
        assertNotNull("Save action was null", saveMenuItem.getAction());
        assertEquals(SaveAction.class, saveMenuItem.getAction().getClass());
    }

    public void testLoadActionIsCorrect() {
        JMenu menu = _constructor.createOutOfBattleMenu();

        JMenuItem loadMenuItem = getWithName(menu, "Load");
        assertNotNull("Load menu item was not added", loadMenuItem);
        assertNotNull("Load action was null", loadMenuItem.getAction());
        assertEquals(LoadAction.class, loadMenuItem.getAction().getClass());
    }

    public void testMoneyActionIsCorrect() {
        JMenu menu = _constructor.createOutOfBattleMenu();

        JMenuItem moneyMenuItem = getWithName(menu, "Money");
        assertNotNull("Money menu item was not added", moneyMenuItem);
        assertNotNull("Money action was null", moneyMenuItem.getAction());
        assertEquals(MoneyAction.class, moneyMenuItem.getAction().getClass());
    }

    public void testBagActionIsCorrect() {
        JMenu menu = _constructor.createOutOfBattleMenu();

        JMenuItem bagMenuItem = getWithName(menu, "Bag");
        assertNotNull("Bag menu item was not added", bagMenuItem);
        assertNotNull("Bag action was null", bagMenuItem.getAction());
        assertEquals(BagAction.class, bagMenuItem.getAction().getClass());
    }

    public void testBlockamoActionIsCorrect() {
        JMenu menu = _constructor.createOutOfBattleMenu();

        JMenuItem blockamonMenuItem = getWithName(menu, "Blockamon");
        assertNotNull("Blockamon menu item was added", blockamonMenuItem);
        assertNotNull("Blockamon action was not set", blockamonMenuItem.getAction());
        assertEquals(BlockamonMenuAction.class, blockamonMenuItem.getAction().getClass());
    }

    public void testCreatesItemMenuWithNoItemsCorrectly() {
        JMenu menu = _constructor.createItemMenu();
        assertNameAndText(menu, "Items", "Items");

        assertEquals("Number of menus was not correct", 1, menu.getItemCount());

        JMenuItem backButton = menu.getItem(0);
        assertNameAndText(backButton, "Back", "Back");
        assertNotNull("Back button was not added", backButton);
        assertNotNull("Back button action was not set", backButton.getAction());
        assertEquals(BackAction.class, backButton.getAction().getClass());
    }

    public void testCreatesItemMenuWithItemsCorrectly() {
        _player.addItem(Item.BLOCKABALL);
        _player.addItem(Item.HEALVIAL);
        JMenu menu = _constructor.createItemMenu();
        assertNameAndText(menu, "Items", "Items");

        assertEquals("Number of menus was not correct", 3, menu.getItemCount());

        assertAllItemsHaveNames(menu);

        JMenuItem blockaballItemMenu = getWithName(menu, "BLOCKABALL");
        assertNotNull("Blockaball item menu was not added", blockaballItemMenu);
        assertEquals("Blockaball text was not right", "Blockaball", blockaballItemMenu.getText());
        assertNotNull("Blockaball action was not set", blockaballItemMenu.getAction());
        assertEquals(ItemAction.class, blockaballItemMenu.getAction().getClass());

        JMenuItem healVialItemMenu = getWithName(menu, "HEALVIAL");
        assertNotNull("HealVial item menu was not added", healVialItemMenu);
        assertEquals("HealVial text was not right", "Heal Vial", healVialItemMenu.getText());
        assertNotNull("HealVial action was not set", healVialItemMenu.getAction());
        assertEquals(ItemAction.class, healVialItemMenu.getAction().getClass());

        JMenuItem backItemMenu = getWithName(menu, "Back");
        assertNotNull("Back item menu was not added", backItemMenu);
        assertEquals("Back item text was not right", "Back", backItemMenu.getText());
        assertNotNull("Back action was not set", backItemMenu.getAction());
        assertEquals(BackAction.class, backItemMenu.getAction().getClass());
    }

    public void testCreatesBlockamonMenuCorrectly() {
        Blockamon blockamon = new Blockamon(ElementType.BUG);
        _player.addToParty(blockamon);
        JMenu menu = _constructor.createBlockamonMenu();

        assertNameAndText(menu, "Blockamon", "Blockamon");

        assertEquals("Number of menu items was not correct", 2, menu.getItemCount());

        JMenuItem blockamonMenuItem = menu.getItem(0);
        assertNameAndText(blockamonMenuItem, blockamon.elementType() + "0",
                blockamon.name() + ", " + blockamon.level() + ", " + blockamon.currentHp() + "/" + blockamon.maxHp());
        assertNotNull("Blockamon menu action not set", blockamonMenuItem.getAction());
        assertEquals(BlockamonAction.class, blockamonMenuItem.getAction().getClass());

        JMenuItem backItemMenu = menu.getItem(1);
        assertNotNull("Back item menu was not added", backItemMenu);
        assertEquals("Back item text was not right", "Back", backItemMenu.getText());
        assertNotNull("Back action was not set", backItemMenu.getAction());
        assertEquals(BackAction.class, backItemMenu.getAction().getClass());
    }

    private void assertNameAndText(JMenu menu, String name, String text) {
        Assert.assertEquals("Name of the Menu should be " + name, name, menu.getName());
        Assert.assertEquals("Text of the Menu should be " + text, text, menu.getText());
    }

    private void assertNameAndText(JMenuItem menuItem, String name, String text) {
        Assert.assertEquals("Name of menu item is " + name, name, menuItem.getName());
        Assert.assertEquals("Text of menu item is " + text, text, menuItem.getText());
    }

    private void assertAllItemsHaveNames(JMenu menu) {
        for(int i = 0; i < menu.getItemCount(); i++) {
            assertNotNull("Item at position " + i + " did not have a name", menu.getItem(i).getName());
        }
    }

    private JMenuItem getWithName(JMenu menu, String name) {
        int numOfItems = menu.getItemCount();
        JMenuItem menuItem = null;
        for(int i = 0; i < numOfItems; i++) {
            JMenuItem item = menu.getItem(i);
            if(item == null) {
                continue;
            }
            if(item.getName() == null) {
                continue;
            }
            if(item.getName().equals(name)) {
                menuItem = item;
                i = numOfItems;
            }
        }
        return menuItem;
    }
}
