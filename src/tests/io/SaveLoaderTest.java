package tests.io;

import blockamon.io.*;
import blockamon.items.Item;
import blockamon.objects.Blockamon;
import blockamon.objects.ElementType;
import blockamon.objects.Player;
import junit.framework.TestCase;
import tests.mocks.MockDirectionalObjectImage;
import tests.mocks.MockFileChooserHandler;
import tests.mocks.MockPlayerCreator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * User: Jack's Computer
 * Date: 4/27/2014
 * Time: 1:49 PM
 */
public class SaveLoaderTest extends TestCase {
    private final String TEST_FILE_NAME = "saveWriterTestFile.bsave";
    private IFileChooserHandler _fileChooserHandler;
    private ISaveLoader _saveLoader;
    private ISaveWriter _saveWriter;
    private Player _playerSaved;

    public SaveLoaderTest() {
        _playerSaved = new Player(new MockDirectionalObjectImage());
        _fileChooserHandler = new MockFileChooserHandler(TEST_FILE_NAME);
        _saveLoader = new SaveLoader(_fileChooserHandler, new MockPlayerCreator());
        _saveWriter = new SaveWriter(_fileChooserHandler);
    }

    public void test_loads_player_with_no_items_and_no_blockamon() {
        _playerSaved.setMoney(2000);
        _playerSaved.setPosition(50, 20);

        try {
            _saveWriter.SaveGame(_playerSaved);

            Player playerLoaded = _saveLoader.LoadSave();
            assertNotNull(playerLoaded);
            assertIsEqualTo(playerLoaded.getMoney(), _playerSaved.getMoney(), "Player Money");
            assertIsEqualTo(playerLoaded.getX(), _playerSaved.getX(), "Player X Position");
            assertIsEqualTo(playerLoaded.getY(), _playerSaved.getY(), "Player Y Position");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_loads_player_with_one_item_and_no_blockamon() {
        _playerSaved.setMoney(2000);
        _playerSaved.setPosition(50, 20);
        _playerSaved.addItem(Item.HEALVIAL);

        try {
            _saveWriter.SaveGame(_playerSaved);

            Player playerLoaded = _saveLoader.LoadSave();
            assertNotNull(playerLoaded);
            assertIsEqualTo(playerLoaded.getMoney(), _playerSaved.getMoney(), "Player Money");
            assertIsEqualTo(playerLoaded.getX(), _playerSaved.getX(), "Player X Position");
            assertIsEqualTo(playerLoaded.getY(), _playerSaved.getY(), "Player Y Position");
            assertIsEqualTo(playerLoaded.getItemCount(Item.HEALVIAL), 1);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_loads_player_with_two_items_and_no_blockamon() {
        _playerSaved.setMoney(2000);
        _playerSaved.setPosition(50, 20);
        _playerSaved.addItem(Item.HEALVIAL);
        _playerSaved.addItem(Item.HEALVIAL);
        _playerSaved.addItem(Item.BLOCKABALL);

        try {
            _saveWriter.SaveGame(_playerSaved);

            Player playerLoaded = _saveLoader.LoadSave();
            assertNotNull(playerLoaded);
            assertIsEqualTo(playerLoaded.getMoney(), _playerSaved.getMoney(), "Player Money");
            assertIsEqualTo(playerLoaded.getX(), _playerSaved.getX(), "Player X Position");
            assertIsEqualTo(playerLoaded.getY(), _playerSaved.getY(), "Player Y Position");
            assertIsEqualTo(playerLoaded.getItemCount(Item.HEALVIAL), 2);
            assertIsEqualTo(playerLoaded.getItemCount(Item.BLOCKABALL), 1);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_loads_player_with_one_item_and_one_blockamon() {
        _playerSaved.setMoney(2000);
        _playerSaved.setPosition(50, 20);
        _playerSaved.addItem(Item.HEALVIAL);

        Blockamon leadBlockamon = new Blockamon(ElementType.BUG);
        leadBlockamon.maxHp(200);
        leadBlockamon.currentHp(20);
        leadBlockamon.level(80);
        leadBlockamon.currentExp(75);
        leadBlockamon.neededExp(80);
        leadBlockamon.totalAttack(20);
        leadBlockamon.name("Blocky Name");
        leadBlockamon.isLead(true);
        _playerSaved.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(_playerSaved);

            Player playerLoaded = _saveLoader.LoadSave();
            assertNotNull(playerLoaded);
            assertIsEqualTo(playerLoaded.getMoney(), _playerSaved.getMoney(), "Player Money");
            assertIsEqualTo(playerLoaded.getX(), _playerSaved.getX(), "Player X Position");
            assertIsEqualTo(playerLoaded.getY(), _playerSaved.getY(), "Player Y Position");
            assertIsEqualTo(playerLoaded.getItemCount(Item.HEALVIAL), 1);

            ArrayList<Blockamon> blockamons = playerLoaded.getBlockamon();
            assertIsEqualTo(blockamons.size(), 1);

            Blockamon firstBlockamon = blockamons.get(0);
            assertBlockamonIsEqualTo(firstBlockamon, leadBlockamon);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_loads_player_with_two_items_and_two_blockamon() {
        _playerSaved.setMoney(2000);
        _playerSaved.setPosition(50, 20);
        _playerSaved.addItem(Item.HEALVIAL);
        _playerSaved.addItem(Item.BLOCKABALL);
        _playerSaved.addItem(Item.BLOCKABALL);

        Blockamon leadBlockamon = new Blockamon(ElementType.BUG);
        leadBlockamon.maxHp(200);
        leadBlockamon.currentHp(20);
        leadBlockamon.level(80);
        leadBlockamon.currentExp(75);
        leadBlockamon.neededExp(80);
        leadBlockamon.totalAttack(20);
        leadBlockamon.name("Blocky Name");
        leadBlockamon.isLead(true);
        _playerSaved.setLeadBlockamon(leadBlockamon);

        Blockamon secondBlockamon = new Blockamon(ElementType.ICE);
        secondBlockamon.maxHp(2000);
        secondBlockamon.currentHp(200);
        secondBlockamon.level(800);
        secondBlockamon.currentExp(705);
        secondBlockamon.neededExp(800);
        secondBlockamon.totalAttack(200);
        secondBlockamon.name("Blocky Namejydjgf");
        secondBlockamon.isLead(false);
        _playerSaved.addToParty(secondBlockamon);

        try {
            _saveWriter.SaveGame(_playerSaved);

            Player playerLoaded = _saveLoader.LoadSave();
            assertNotNull(playerLoaded);
            assertIsEqualTo(playerLoaded.getMoney(), _playerSaved.getMoney(), "Player Money");
            assertIsEqualTo(playerLoaded.getX(), _playerSaved.getX(), "Player X Position");
            assertIsEqualTo(playerLoaded.getY(), _playerSaved.getY(), "Player Y Position");
            assertIsEqualTo(playerLoaded.getItemCount(Item.HEALVIAL), 1);

            ArrayList<Blockamon> blockamons = playerLoaded.getBlockamon();
            assertIsEqualTo(blockamons.size(), 2);

            Blockamon firstBlockamon = blockamons.get(0);
            assertBlockamonIsEqualTo(firstBlockamon, leadBlockamon);

            Blockamon secondBlockamonLoaded = blockamons.get(1);
            assertBlockamonIsEqualTo(secondBlockamonLoaded, secondBlockamon);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    private void assertBlockamonIsEqualTo(Blockamon actual, Blockamon expected) {
        assertIsEqualTo(actual.elementType(), expected.elementType());
        assertIsEqualTo(actual.maxHp(), expected.maxHp());
        assertIsEqualTo(actual.currentHp(), expected.currentHp());
        assertIsEqualTo(actual.level(), expected.level());
        assertIsEqualTo(actual.currentExp(), expected.currentExp());
        assertIsEqualTo(actual.neededExp(), expected.neededExp());
        assertIsEqualTo(actual.totalAttack(), expected.totalAttack());
        assertIsEqualTo(actual.name(), expected.name());
        assertIsEqualTo(actual.isLead(), expected.isLead());
    }

    private void assertIsEqualTo(Object actual, Object expected) {
        assertEquals(expected, actual);
    }

    private void assertIsEqualTo(Object actual, Object expected, String message) {
        assertEquals(message, expected, actual);
    }

    protected void tearDown() throws Exception {
        File file = new File(TEST_FILE_NAME);
        if(file.exists()) {
            file.delete();
        }
        super.tearDown();
    }
}
