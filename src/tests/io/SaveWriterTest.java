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
import tests.mocks.MockFileChooserHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SaveWriterTest extends TestCase {
    private final String TEST_FILE_NAME = "saveWriterTestFile.bsave";
    private IFileChooserHandler _fileChooserHandler;
    private ISaveWriter _saveWriter;

    public SaveWriterTest() {
        _fileChooserHandler = new MockFileChooserHandler(TEST_FILE_NAME);
        _saveWriter = new SaveWriter(_fileChooserHandler);
    }

    /**
     * Format:
     * Player
     * Money:2500.0
     * Position:8,10
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
        Player player = new Player();
        Blockamon leadBlockamon = new Blockamon(ElementType.BUG);
        leadBlockamon.maxHitPoints(800);
        leadBlockamon.currentHitPoints(80);
        leadBlockamon.setCurrentLevel(17);
        player.setPosition(8, 5);
        player.setMoney(200);
        player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(player);

            File loadedGame = _fileChooserHandler.getLoadFile();
            assertTrue(loadedGame.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadedGame);
            assertLineIsEqualTo(lines.size(), 12);

            int currentLine = 0;
            assertLineIsEqualTo(lines.get(currentLine++), "Player");
            assertLineIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:8,5");
            assertLineIsEqualTo(lines.get(currentLine++), "Blockamon");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'BUG'");
            assertLineIsEqualTo(lines.get(currentLine++), "Type:'BUG'");
            assertLineIsEqualTo(lines.get(currentLine++), "Status:'NONE'");
            assertLineIsEqualTo(lines.get(currentLine++), "CurrentHealth:80");
            assertLineIsEqualTo(lines.get(currentLine++), "TotalHealth:800");
            assertLineIsEqualTo(lines.get(currentLine++), "Level:17");
            assertLineIsEqualTo(lines.get(currentLine++), "IsLead:true");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:0");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("File wasn't written to disk");
        }
    }

    public void test_saves_player_with_no_items_and_no_blockamon_to_disk() {
        Player player = new Player();
        player.setMoney(200);
        player.setPosition(50, 20);

        try {
            _saveWriter.SaveGame(player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertLineIsEqualTo(lines.size(), 3);

            int currentLine = 0;
            assertLineIsEqualTo(lines.get(currentLine++), "Player");
            assertLineIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:50,20");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_one_item_and_no_blockamon_to_disk() {
        Player player = new Player();
        player.setMoney(200);
        player.setPosition(50, 20);
        player.addItem(Item.HEALVIAL);

        try {
            _saveWriter.SaveGame(player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertLineIsEqualTo(lines.size(), 6);

            int currentLine = 0;
            assertLineIsEqualTo(lines.get(currentLine++), "Player");
            assertLineIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:50,20");
            assertLineIsEqualTo(lines.get(currentLine++), "Item");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'" + Item.HEALVIAL.getName() + "'");
            assertLineIsEqualTo(lines.get(currentLine++), "Count:1");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_two_items_and_no_blockamon_to_disk() {
        Player player = new Player();
        player.setMoney(200);
        player.setPosition(50, 20);
        player.addItem(Item.HEALVIAL);
        player.addItem(Item.HEALVIAL);
        player.addItem(Item.BLOCKABALL);

        try {
            _saveWriter.SaveGame(player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertLineIsEqualTo(lines.size(), 9);

            int currentLine = 0;
            assertLineIsEqualTo(lines.get(currentLine++), "Player");
            assertLineIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:50,20");
            assertLineIsEqualTo(lines.get(currentLine++), "Item");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'" + Item.HEALVIAL.getName() + "'");
            assertLineIsEqualTo(lines.get(currentLine++), "Count:2");
            assertLineIsEqualTo(lines.get(currentLine++), "Item");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'" + Item.BLOCKABALL.getName() + "'");
            assertLineIsEqualTo(lines.get(currentLine++), "Count:1");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_one_item_and_one_blockamon_to_disk() {
        Player player = new Player();
        player.setMoney(200);
        player.setPosition(50, 20);
        player.addItem(Item.HEALVIAL);
        Blockamon leadBlockamon = new Blockamon(ElementType.BUG);
        leadBlockamon.maxHitPoints(800);
        leadBlockamon.currentHitPoints(80);
        leadBlockamon.setCurrentLevel(17);
        player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertLineIsEqualTo(lines.size(), 15);

            int currentLine = 0;
            assertLineIsEqualTo(lines.get(currentLine++), "Player");
            assertLineIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:50,20");
            assertLineIsEqualTo(lines.get(currentLine++), "Item");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'" + Item.HEALVIAL.getName() + "'");
            assertLineIsEqualTo(lines.get(currentLine++), "Count:1");
            assertLineIsEqualTo(lines.get(currentLine++), "Blockamon");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'BUG'");
            assertLineIsEqualTo(lines.get(currentLine++), "Type:'BUG'");
            assertLineIsEqualTo(lines.get(currentLine++), "Status:'NONE'");
            assertLineIsEqualTo(lines.get(currentLine++), "CurrentHealth:80");
            assertLineIsEqualTo(lines.get(currentLine++), "TotalHealth:800");
            assertLineIsEqualTo(lines.get(currentLine++), "Level:17");
            assertLineIsEqualTo(lines.get(currentLine++), "IsLead:true");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:0");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_two_items_and_one_blockamon_to_disk() {
        Player player = new Player();
        player.setMoney(200);
        player.setPosition(50, 20);
        player.addItem(Item.HEALVIAL);
        player.addItem(Item.HEALVIAL);
        player.addItem(Item.BLOCKABALL);
        Blockamon leadBlockamon = new Blockamon(ElementType.BUG);
        leadBlockamon.maxHitPoints(800);
        leadBlockamon.currentHitPoints(80);
        leadBlockamon.setCurrentLevel(17);
        player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertLineIsEqualTo(lines.size(), 18);

            int currentLine = 0;
            assertLineIsEqualTo(lines.get(currentLine++), "Player");
            assertLineIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:50,20");
            assertLineIsEqualTo(lines.get(currentLine++), "Item");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'" + Item.HEALVIAL.getName() + "'");
            assertLineIsEqualTo(lines.get(currentLine++), "Count:2");
            assertLineIsEqualTo(lines.get(currentLine++), "Item");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'" + Item.BLOCKABALL.getName() + "'");
            assertLineIsEqualTo(lines.get(currentLine++), "Count:1");
            assertLineIsEqualTo(lines.get(currentLine++), "Blockamon");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'BUG'");
            assertLineIsEqualTo(lines.get(currentLine++), "Type:'BUG'");
            assertLineIsEqualTo(lines.get(currentLine++), "Status:'NONE'");
            assertLineIsEqualTo(lines.get(currentLine++), "CurrentHealth:80");
            assertLineIsEqualTo(lines.get(currentLine++), "TotalHealth:800");
            assertLineIsEqualTo(lines.get(currentLine++), "Level:17");
            assertLineIsEqualTo(lines.get(currentLine++), "IsLead:true");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:0");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_one_item_and_two_blockamons_to_disk() {
        Player player = new Player();
        player.setMoney(200);
        player.setPosition(50, 20);
        player.addItem(Item.HEALVIAL);
        Blockamon firstBlockamon = new Blockamon(ElementType.BUG);
        firstBlockamon.maxHitPoints(800);
        firstBlockamon.currentHitPoints(80);
        firstBlockamon.setCurrentLevel(17);
        player.setLeadBlockamon(firstBlockamon);

        Blockamon leadBlockamon = new Blockamon(ElementType.ICE);
        leadBlockamon.maxHitPoints(8000);
        leadBlockamon.currentHitPoints(800);
        leadBlockamon.setCurrentLevel(20);
        player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertLineIsEqualTo(lines.size(), 24);

            int currentLine = 0;
            assertLineIsEqualTo(lines.get(currentLine++), "Player");
            assertLineIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:50,20");

            assertLineIsEqualTo(lines.get(currentLine++), "Item");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'" + Item.HEALVIAL.getName() + "'");
            assertLineIsEqualTo(lines.get(currentLine++), "Count:1");

            assertLineIsEqualTo(lines.get(currentLine++), "Blockamon");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'ICE'");
            assertLineIsEqualTo(lines.get(currentLine++), "Type:'ICE'");
            assertLineIsEqualTo(lines.get(currentLine++), "Status:'NONE'");
            assertLineIsEqualTo(lines.get(currentLine++), "CurrentHealth:800");
            assertLineIsEqualTo(lines.get(currentLine++), "TotalHealth:8000");
            assertLineIsEqualTo(lines.get(currentLine++), "Level:20");
            assertLineIsEqualTo(lines.get(currentLine++), "IsLead:true");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:0");

            assertLineIsEqualTo(lines.get(currentLine++), "Blockamon");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'BUG'");
            assertLineIsEqualTo(lines.get(currentLine++), "Type:'BUG'");
            assertLineIsEqualTo(lines.get(currentLine++), "Status:'NONE'");
            assertLineIsEqualTo(lines.get(currentLine++), "CurrentHealth:80");
            assertLineIsEqualTo(lines.get(currentLine++), "TotalHealth:800");
            assertLineIsEqualTo(lines.get(currentLine++), "Level:17");
            assertLineIsEqualTo(lines.get(currentLine++), "IsLead:false");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:1");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }

    public void test_saves_player_with_two_items_and_two_blockamons_to_disk() {
        Player player = new Player();
        player.setMoney(200);
        player.setPosition(50, 20);
        player.addItem(Item.HEALVIAL);
        player.addItem(Item.HEALVIAL);
        player.addItem(Item.BLOCKABALL);
        Blockamon firstBlockamon = new Blockamon(ElementType.BUG);
        firstBlockamon.maxHitPoints(800);
        firstBlockamon.currentHitPoints(80);
        firstBlockamon.setCurrentLevel(17);
        player.setLeadBlockamon(firstBlockamon);

        Blockamon leadBlockamon = new Blockamon(ElementType.ICE);
        leadBlockamon.maxHitPoints(8000);
        leadBlockamon.currentHitPoints(800);
        leadBlockamon.setCurrentLevel(20);
        player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = TestExtensions.readAllLines(loadFile);
            assertLineIsEqualTo(lines.size(), 27);

            int currentLine = 0;
            assertLineIsEqualTo(lines.get(currentLine++), "Player");
            assertLineIsEqualTo(lines.get(currentLine++), "Money:200.0");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:50,20");

            assertLineIsEqualTo(lines.get(currentLine++), "Item");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'" + Item.HEALVIAL.getName() + "'");
            assertLineIsEqualTo(lines.get(currentLine++), "Count:2");
            assertLineIsEqualTo(lines.get(currentLine++), "Item");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'" + Item.BLOCKABALL.getName() + "'");
            assertLineIsEqualTo(lines.get(currentLine++), "Count:1");

            assertLineIsEqualTo(lines.get(currentLine++), "Blockamon");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'ICE'");
            assertLineIsEqualTo(lines.get(currentLine++), "Type:'ICE'");
            assertLineIsEqualTo(lines.get(currentLine++), "Status:'NONE'");
            assertLineIsEqualTo(lines.get(currentLine++), "CurrentHealth:800");
            assertLineIsEqualTo(lines.get(currentLine++), "TotalHealth:8000");
            assertLineIsEqualTo(lines.get(currentLine++), "Level:20");
            assertLineIsEqualTo(lines.get(currentLine++), "IsLead:true");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:0");

            assertLineIsEqualTo(lines.get(currentLine++), "Blockamon");
            assertLineIsEqualTo(lines.get(currentLine++), "Name:'BUG'");
            assertLineIsEqualTo(lines.get(currentLine++), "Type:'BUG'");
            assertLineIsEqualTo(lines.get(currentLine++), "Status:'NONE'");
            assertLineIsEqualTo(lines.get(currentLine++), "CurrentHealth:80");
            assertLineIsEqualTo(lines.get(currentLine++), "TotalHealth:800");
            assertLineIsEqualTo(lines.get(currentLine++), "Level:17");
            assertLineIsEqualTo(lines.get(currentLine++), "IsLead:false");
            assertLineIsEqualTo(lines.get(currentLine++), "Position:1");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }
    private void assertLineIsEqualTo(Object actual, Object expected) {
        assertEquals(expected, actual);
    }

    protected void tearDown() throws Exception {
        File file = new File(TEST_FILE_NAME);
        if(file.exists()) {
            file.delete();
        }
        super.tearDown();
    }
}
