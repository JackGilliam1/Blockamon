package tests.io;

import blockamon.io.IFileChooserHandler;
import blockamon.io.ISaveWriter;
import blockamon.io.SaveWriter;
import blockamon.items.Item;
import blockamon.objects.Blockamon;
import blockamon.objects.ElementType;
import blockamon.objects.Player;
import junit.framework.TestCase;
import tests.TestExtensions;
import tests.mocks.MockDirectionalObjectImage;
import tests.mocks.MockFileChooserHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SaveWriterTest extends TestCase {
    private final String TEST_FILE_NAME = "saveWriterTestFile.bsave";
    private IFileChooserHandler _fileChooserHandler;
    private ISaveWriter _saveWriter;
    private Player _player;

    public SaveWriterTest() {
        _player = new Player(new MockDirectionalObjectImage());
        _fileChooserHandler = new MockFileChooserHandler(TEST_FILE_NAME);
        _saveWriter = new SaveWriter(_fileChooserHandler);
    }

    /**
     * Format:
     * Player
     * Money:2500.0
     * PositionX:8
     * PositionY:10
     *
     * Item
     * Name:'Some Item'
     * Count:10
     *
     * Item
     * Name:'Some Other Item'
     * Count:20
     *
     * Blockamon
     * Name:'Some Blockamon Name'
     * Type:'Fire'
     * Status:'FAINTED'
     * CurrentHealth:0
     * TotalHealth:800
     * Level:14
     * CurrentEXP:20
     * EXPNeeded:200
     * TotalAttack:18
     * IsLead:true
     * Position:0
     *
     * Blockamon
     * Name:'Some Other Name'
     * Type:'Ice'
     * Status:'NONE'
     * CurrentHealth:88
     * TotalHealth:800
     * Level:17
     * IsLead:false
     * Position:1
     */
    public void test_saves_player_with_no_items_and_one_blockamon_to_disk() {
        _player.setPosition(8, 5);
        _player.setMoney(200);

        Blockamon leadBlockamon = new Blockamon(ElementType.BUG);
        leadBlockamon.maxHP(800);
        leadBlockamon.currentHitPoints(80);
        leadBlockamon.setCurrentLevel(17);
        leadBlockamon.setExperience(80);
        leadBlockamon.setNeededExperience(300);
        leadBlockamon.setTotalAttack(15);
        leadBlockamon.isLead(true);
        _player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadedGame = _fileChooserHandler.getLoadFile();
            assertTrue(loadedGame.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadedGame);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            int currentLine = 0;
            assertIsEqualTo(lines.get(currentLine++), "Player");
            assertIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertIsEqualTo(lines.get(currentLine++), "PositionX:8");
            assertIsEqualTo(lines.get(currentLine++), "PositionY:5");

            currentLine = assertBlockamonLinesAreEqual(lines, leadBlockamon, 0, currentLine);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("File wasn't written to disk");
        }
    }

    public void test_saves_player_with_no_items_and_no_blockamon_to_disk() {
        _player.setMoney(200);
        _player.setPosition(50, 20);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            int currentLine = 0;
            assertIsEqualTo(lines.get(currentLine++), "Player");
            assertIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertIsEqualTo(lines.get(currentLine++), "PositionX:50");
            assertIsEqualTo(lines.get(currentLine++), "PositionY:20");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_one_item_and_no_blockamon_to_disk() {
        _player.setMoney(200);
        _player.setPosition(50, 20);
        _player.addItem(Item.HEALVIAL);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            int currentLine = 0;
            assertIsEqualTo(lines.get(currentLine++), "Player");
            assertIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertIsEqualTo(lines.get(currentLine++), "PositionX:50");
            assertIsEqualTo(lines.get(currentLine++), "PositionY:20");

            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 1, currentLine);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_two_items_and_no_blockamon_to_disk() {
        _player.setMoney(200);
        _player.setPosition(50, 20);
        _player.addItem(Item.HEALVIAL);
        _player.addItem(Item.HEALVIAL);
        _player.addItem(Item.BLOCKABALL);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            int currentLine = 0;
            assertIsEqualTo(lines.get(currentLine++), "Player");
            assertIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertIsEqualTo(lines.get(currentLine++), "PositionX:50");
            assertIsEqualTo(lines.get(currentLine++), "PositionY:20");

            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 2, currentLine);

            currentLine = assertItemLinesAreEqual(lines, Item.BLOCKABALL, 1, currentLine);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_one_item_and_one_blockamon_to_disk() {
        _player.setMoney(200);
        _player.setPosition(50, 20);
        _player.addItem(Item.HEALVIAL);

        Blockamon leadBlockamon = new Blockamon(ElementType.BUG);
        leadBlockamon.maxHP(800);
        leadBlockamon.currentHitPoints(80);
        leadBlockamon.setCurrentLevel(17);
        leadBlockamon.setExperience(80);
        leadBlockamon.setNeededExperience(300);
        leadBlockamon.setTotalAttack(15);
        leadBlockamon.isLead(true);
        _player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            int currentLine = 0;
            assertIsEqualTo(lines.get(currentLine++), "Player");
            assertIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertIsEqualTo(lines.get(currentLine++), "PositionX:50");
            assertIsEqualTo(lines.get(currentLine++), "PositionY:20");

            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 1, currentLine);

            currentLine = assertBlockamonLinesAreEqual(lines, leadBlockamon, 0, currentLine);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_two_items_and_one_blockamon_to_disk() {
        _player.setMoney(200);
        _player.setPosition(50, 20);
        _player.addItem(Item.HEALVIAL);
        _player.addItem(Item.HEALVIAL);
        _player.addItem(Item.BLOCKABALL);

        Blockamon leadBlockamon = new Blockamon(ElementType.BUG);
        leadBlockamon.maxHP(800);
        leadBlockamon.currentHitPoints(80);
        leadBlockamon.setCurrentLevel(17);
        leadBlockamon.setExperience(80);
        leadBlockamon.setNeededExperience(300);
        leadBlockamon.setTotalAttack(15);
        leadBlockamon.isLead(true);
        _player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            int currentLine = 0;
            assertIsEqualTo(lines.get(currentLine++), "Player");
            assertIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertIsEqualTo(lines.get(currentLine++), "PositionX:50");
            assertIsEqualTo(lines.get(currentLine++), "PositionY:20");

            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 2, currentLine);

            currentLine = assertItemLinesAreEqual(lines, Item.BLOCKABALL, 1, currentLine);

            currentLine = assertBlockamonLinesAreEqual(lines, leadBlockamon, 0, currentLine);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_one_item_and_two_blockamons_to_disk() {
        _player.setMoney(200);
        _player.setPosition(50, 20);
        _player.addItem(Item.HEALVIAL);

        Blockamon firstBlockamon = new Blockamon(ElementType.BUG);
        firstBlockamon.maxHP(800);
        firstBlockamon.currentHitPoints(80);
        firstBlockamon.setCurrentLevel(17);
        firstBlockamon.setExperience(20);
        firstBlockamon.setNeededExperience(200);
        firstBlockamon.setTotalAttack(18);
        firstBlockamon.isLead(false);
        _player.setLeadBlockamon(firstBlockamon);

        Blockamon leadBlockamon = new Blockamon(ElementType.ICE);
        leadBlockamon.maxHP(8000);
        leadBlockamon.currentHitPoints(800);
        leadBlockamon.setCurrentLevel(20);
        leadBlockamon.setExperience(80);
        leadBlockamon.setNeededExperience(300);
        leadBlockamon.setTotalAttack(15);
        leadBlockamon.isLead(true);
        _player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            int currentLine = 0;
            assertIsEqualTo(lines.get(currentLine++), "Player");
            assertIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertIsEqualTo(lines.get(currentLine++), "PositionX:50");
            assertIsEqualTo(lines.get(currentLine++), "PositionY:20");

            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 1, currentLine);

            currentLine = assertBlockamonLinesAreEqual(lines, leadBlockamon, 0, currentLine);

            currentLine = assertBlockamonLinesAreEqual(lines, firstBlockamon, 1, currentLine);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_two_items_and_two_blockamons_to_disk() {
        _player.setMoney(200);
        _player.setPosition(50, 20);
        _player.addItem(Item.HEALVIAL);
        _player.addItem(Item.HEALVIAL);
        _player.addItem(Item.BLOCKABALL);

        Blockamon firstBlockamon = new Blockamon(ElementType.BUG);
        firstBlockamon.maxHP(800);
        firstBlockamon.currentHitPoints(80);
        firstBlockamon.setCurrentLevel(17);
        firstBlockamon.setExperience(20);
        firstBlockamon.setNeededExperience(200);
        firstBlockamon.setTotalAttack(12);
        firstBlockamon.isLead(false);
        _player.setLeadBlockamon(firstBlockamon);

        Blockamon leadBlockamon = new Blockamon(ElementType.ICE);
        leadBlockamon.maxHP(8000);
        leadBlockamon.currentHitPoints(800);
        leadBlockamon.setCurrentLevel(20);
        leadBlockamon.setExperience(80);
        leadBlockamon.setNeededExperience(300);
        leadBlockamon.setTotalAttack(15);
        leadBlockamon.isLead(true);
        _player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            int currentLine = 0;
            assertIsEqualTo(lines.get(currentLine++), "Player");
            assertIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertIsEqualTo(lines.get(currentLine++), "PositionX:50");
            assertIsEqualTo(lines.get(currentLine++), "PositionY:20");

            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 2, currentLine);

            currentLine = assertItemLinesAreEqual(lines, Item.BLOCKABALL, 1, currentLine);

            currentLine = assertBlockamonLinesAreEqual(lines, leadBlockamon, 0, currentLine);

            currentLine = assertBlockamonLinesAreEqual(lines, firstBlockamon, 1, currentLine);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    private int assertItemLinesAreEqual(ArrayList<String> lines, Item item, int count, int currentLine) {
        assertIsEqualTo(lines.get(currentLine++), "Item");
        assertIsEqualTo(lines.get(currentLine++), "Name:'" + item.toString() + "'");
        assertIsEqualTo(lines.get(currentLine++), "Count:" + count);
        return currentLine;
    }

    private int assertBlockamonLinesAreEqual(ArrayList<String> lines, Blockamon blockamon, int position, int currentLine) {
        assertIsEqualTo(lines.get(currentLine++), "Blockamon");
        assertIsEqualTo(lines.get(currentLine++), "Name:'" + blockamon.getName() + "'");
        assertIsEqualTo(lines.get(currentLine++), "Type:'" + blockamon.element().toString() + "'");
        assertIsEqualTo(lines.get(currentLine++), "Status:'" + blockamon.getStatus() + "'");
        assertIsEqualTo(lines.get(currentLine++), "CurrentHealth:" + blockamon.currentHitPoints());
        assertIsEqualTo(lines.get(currentLine++), "TotalHealth:" + blockamon.maxHP());
        assertIsEqualTo(lines.get(currentLine++), "Level:" + blockamon.getCurrentLevel());
        assertIsEqualTo(lines.get(currentLine++), "CurrentEXP:" + blockamon.getExperience());
        assertIsEqualTo(lines.get(currentLine++), "EXPNeeded:" + blockamon.getNeededExperience());
        assertIsEqualTo(lines.get(currentLine++), "TotalAttack:" + blockamon.getTotalAttack());
        assertIsEqualTo(lines.get(currentLine++), "IsLead:" + blockamon.isLead());
        assertIsEqualTo(lines.get(currentLine++), "Position:" + position);
        return currentLine;
    }

    private void assertIsEqualTo(Object actual, Object expected) {
        assertEquals(expected, actual);
    }

    private int calculateExpectedLineCount() {
        return 4 + (_player.getBlockamon().size() * 12) + (_player.getItems().length * 3);
    }

    protected void tearDown() throws Exception {
        File file = new File(TEST_FILE_NAME);
        if(file.exists()) {
            file.delete();
        }
        super.tearDown();
    }
}
