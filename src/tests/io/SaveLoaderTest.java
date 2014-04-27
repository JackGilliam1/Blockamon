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
        leadBlockamon.maxHP(200);
        leadBlockamon.currentHitPoints(20);
        leadBlockamon.setCurrentLevel(80);
        leadBlockamon.setExperience(75);
        leadBlockamon.setNeededExperience(80);
        leadBlockamon.setTotalAttack(20);
        leadBlockamon.setName("Blocky Name");
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
        leadBlockamon.maxHP(200);
        leadBlockamon.currentHitPoints(20);
        leadBlockamon.setCurrentLevel(80);
        leadBlockamon.setExperience(75);
        leadBlockamon.setNeededExperience(80);
        leadBlockamon.setTotalAttack(20);
        leadBlockamon.setName("Blocky Name");
        leadBlockamon.isLead(true);
        _playerSaved.setLeadBlockamon(leadBlockamon);

        Blockamon secondBlockamon = new Blockamon(ElementType.ICE);
        secondBlockamon.maxHP(2000);
        secondBlockamon.currentHitPoints(200);
        secondBlockamon.setCurrentLevel(800);
        secondBlockamon.setExperience(705);
        secondBlockamon.setNeededExperience(800);
        secondBlockamon.setTotalAttack(200);
        secondBlockamon.setName("Blocky Namejydjgf");
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
        assertIsEqualTo(actual.getElementType(), expected.getElementType());
        assertIsEqualTo(actual.maxHP(), expected.maxHP());
        assertIsEqualTo(actual.currentHitPoints(), expected.currentHitPoints());
        assertIsEqualTo(actual.getCurrentLevel(), expected.getCurrentLevel());
        assertIsEqualTo(actual.getExperience(), expected.getExperience());
        assertIsEqualTo(actual.getNeededExperience(), expected.getNeededExperience());
        assertIsEqualTo(actual.getTotalAttack(), expected.getTotalAttack());
        assertIsEqualTo(actual.getName(), expected.getName());
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
