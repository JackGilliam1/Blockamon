package tests.io;

import blockamon.io.IFileChooserHandler;
import blockamon.io.ISaveWriter;
import blockamon.io.SaveWriter;
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
     * Things to save:
     * Player:
     *  Money
     *  Position:
     *      X, Y
     *  Items:
     *      Type, Count
     *  Blockamon:
     *      Name, Type, Status, CurrentHealth, TotalHealth, Level, IsLead
     *  Blockamon Team:
     *      Order
     */
    /**
     * Format:
     * Player
     * Money:2500
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

        _saveWriter.SaveGame(player);

        File loadedGame = _fileChooserHandler.getLoadFile();
        assertTrue(loadedGame.exists());
        try {
            ArrayList<String> linesRead = TestExtensions.readAllLines(loadedGame);
            assertEquals(12, linesRead.size());
            int currentIndex = 0;
            assertEquals(linesRead.get(currentIndex++), "Player");
            assertEquals(linesRead.get(currentIndex++), "Money:200");
            assertEquals(linesRead.get(currentIndex++), "Position:8,5");
            assertEquals(linesRead.get(currentIndex++), "Blockamon");
            assertEquals(linesRead.get(currentIndex++), "Name:'BUG'");
            assertEquals(linesRead.get(currentIndex++), "Type:'BUG'");
            assertEquals(linesRead.get(currentIndex++), "Status:'NONE'");
            assertEquals(linesRead.get(currentIndex++), "CurrentHealth:80");
            assertEquals(linesRead.get(currentIndex++), "TotalHealth:800");
            assertEquals(linesRead.get(currentIndex++), "Level:17");
            assertEquals(linesRead.get(currentIndex++), "IsLead:true");
            assertEquals(linesRead.get(currentIndex++), "Position:0");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("File wasn't written to disk");
        }
    }

    protected void tearDown() throws Exception {
        File file = new File(TEST_FILE_NAME);
        if(file.exists()) {
            file.delete();
        }
        super.tearDown();
    }
}
