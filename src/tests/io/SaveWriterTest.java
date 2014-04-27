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
        Blockamon leadBlockamon = new Blockamon(ElementType.BUG);
        leadBlockamon.maxHitPoints(800);
        leadBlockamon.currentHitPoints(80);
        leadBlockamon.setCurrentLevel(17);
        Player player = new Player();
        player.setPosition(8, 5);
        player.setMoney(200);
        player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(player);

            File loadedGame = _fileChooserHandler.getLoadFile();
            assertTrue(loadedGame.exists());

            ArrayList<String> linesRead = TestExtensions.readAllLines(loadedGame);
            assertLineIsEqualTo(linesRead.size(), 12);

            int currentIndex = 0;
            assertLineIsEqualTo(linesRead.get(currentIndex++), "Player");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "Money:200.0");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "Position:8,5");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "Blockamon");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "Name:'BUG'");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "Type:'BUG'");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "Status:'NONE'");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "CurrentHealth:80");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "TotalHealth:800");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "Level:17");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "IsLead:true");
            assertLineIsEqualTo(linesRead.get(currentIndex++), "Position:0");
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
