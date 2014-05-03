package tests.io;

import blockamon.Extensions;
import blockamon.io.IFileChooserHandler;
import blockamon.io.ISaveWriter;
import blockamon.io.SaveWriter;
import blockamon.items.Item;
import blockamon.objects.Blockamon;
import blockamon.objects.ElementType;
import blockamon.objects.Player;
import blockamon.objects.data.BlockamonData;
import junit.framework.TestCase;
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

    public void test_saves_player_with_no_items_and_no_blockamon_to_disk() {
        _player.setMoney(200);
        _player.setPosition(50, 20);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = Extensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            assertPlayerLineIsEqual(lines, _player);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }
    public void test_saves_player_with_no_items_and_one_blockamon_to_disk() {
        _player.setPosition(8, 5);
        _player.setMoney(200);

        BlockamonData data = getTestBlockamonData(ElementType.BUG, false, 1);
        Blockamon blockamon = getTestBlockamon(data);
        _player.setLeadBlockamon(blockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadedGame = _fileChooserHandler.getLoadFile();
            assertTrue(loadedGame.exists());

            ArrayList<String> lines = Extensions.readAllLines(loadedGame);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            assertPlayerLineIsEqual(lines, _player);

            int currentLine = 1;
            currentLine = assertBlockamonLinesAreEqual(lines, blockamon, 0, currentLine);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("File wasn't written to disk");
        }
    }
    public void test_saves_player_with_no_items_and_two_blockamon_to_disk() {
        _player.setMoney(200);
        _player.setPosition(8, 5);

        BlockamonData data = getTestBlockamonData(ElementType.BUG, false, 1);
        Blockamon firstBlockamon = getTestBlockamon(data);
        _player.setLeadBlockamon(firstBlockamon);

        BlockamonData leadBlockamonData = getTestBlockamonData(ElementType.ICE, true, 2);
        Blockamon leadBlockamon = getTestBlockamon(leadBlockamonData);
        _player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadedGame = _fileChooserHandler.getLoadFile();
            assertTrue(loadedGame.exists());

            ArrayList<String> lines = Extensions.readAllLines(loadedGame);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            assertPlayerLineIsEqual(lines, _player);

            int currentLine = 1;
            currentLine = assertBlockamonLinesAreEqual(lines, leadBlockamon, 0, currentLine);
            currentLine = assertBlockamonLinesAreEqual(lines, firstBlockamon, 1, currentLine);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("File wasn't written to disk");
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

            ArrayList<String> lines = Extensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            assertPlayerLineIsEqual(lines, _player);

            int currentLine = 1;
            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 1, currentLine);
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

        BlockamonData data = getTestBlockamonData(ElementType.BUG, false, 1);
        Blockamon blockamon = getTestBlockamon(data);
        _player.setLeadBlockamon(blockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = Extensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            assertPlayerLineIsEqual(lines, _player);

            int currentLine = 1;
            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 1, currentLine);

            currentLine = assertBlockamonLinesAreEqual(lines, blockamon, 0, currentLine);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
            fail("IOException happened");
        }
    }
    public void test_saves_player_with_one_item_and_two_blockamons_to_disk() {
        _player.setMoney(200.0);
        _player.setPosition(50, 20);
        _player.addItem(Item.HEALVIAL);

        BlockamonData data = getTestBlockamonData(ElementType.BUG, false, 1);
        Blockamon firstBlockamon = getTestBlockamon(data);
        _player.setLeadBlockamon(firstBlockamon);

        BlockamonData leadBlockamonData = getTestBlockamonData(ElementType.ICE, true, 2);
        Blockamon leadBlockamon = getTestBlockamon(leadBlockamonData);
        _player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = Extensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            assertPlayerLineIsEqual(lines, _player);

            int currentLine = 1;
            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 1, currentLine);

            currentLine = assertBlockamonLinesAreEqual(lines, leadBlockamon, 0, currentLine);

            currentLine = assertBlockamonLinesAreEqual(lines, firstBlockamon, 1, currentLine);
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

            ArrayList<String> lines = Extensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            assertPlayerLineIsEqual(lines, _player);

            int currentLine = 1;
            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 2, currentLine);

            currentLine = assertItemLinesAreEqual(lines, Item.BLOCKABALL, 1, currentLine);
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

        BlockamonData data = getTestBlockamonData(ElementType.BUG, false, 1);
        Blockamon leadBlockamon = getTestBlockamon(data);
        _player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = Extensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            assertPlayerLineIsEqual(lines, _player);

            int currentLine = 1;
            currentLine = assertItemLinesAreEqual(lines, Item.HEALVIAL, 2, currentLine);

            currentLine = assertItemLinesAreEqual(lines, Item.BLOCKABALL, 1, currentLine);

            currentLine = assertBlockamonLinesAreEqual(lines, leadBlockamon, 0, currentLine);
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

        BlockamonData data = getTestBlockamonData(ElementType.BUG, false, 1);
        Blockamon firstBlockamon = getTestBlockamon(data);
        _player.setLeadBlockamon(firstBlockamon);

        BlockamonData leadBlockamonData = getTestBlockamonData(ElementType.ICE, true, 2);
        Blockamon leadBlockamon = getTestBlockamon(leadBlockamonData);
        _player.setLeadBlockamon(leadBlockamon);

        try {
            _saveWriter.SaveGame(_player);

            File loadFile = _fileChooserHandler.getLoadFile();
            assertTrue(loadFile.exists());

            ArrayList<String> lines = Extensions.readAllLines(loadFile);
            assertIsEqualTo(lines.size(), calculateExpectedLineCount());

            assertPlayerLineIsEqual(lines, _player);

            int currentLine = 1;
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

    private BlockamonData getTestBlockamonData(ElementType elementType, boolean isLead, int delta) {
        BlockamonData leadBlockamonData = new BlockamonData();
        leadBlockamonData.type = elementType;
        leadBlockamonData.maxHP = 8000 * delta;
        leadBlockamonData.currentHP = 80 * delta;
        leadBlockamonData.currentLevel = 20 * delta;
        leadBlockamonData.experience = 80 * delta;
        leadBlockamonData.neededExperience = 300 * delta;
        leadBlockamonData.totalAttack = 15 * delta;
        leadBlockamonData.isLead = isLead;
        return leadBlockamonData;
    }

    private Blockamon getTestBlockamon(BlockamonData data) {
        Blockamon firstBlockamon;
        firstBlockamon = new Blockamon(data.type);
        firstBlockamon.maxHp(data.maxHP);
        firstBlockamon.currentHp(data.currentHP);
        firstBlockamon.level(data.currentLevel);
        firstBlockamon.currentExp(data.experience);
        firstBlockamon.neededExp(data.neededExperience);
        firstBlockamon.totalAttack(data.totalAttack);
        firstBlockamon.isLead(data.isLead);
        return firstBlockamon;
    }

    private void assertPlayerLineIsEqual(ArrayList<String> lines, Player player) {
        String expectedLine = String
                .format(SaveWriter.PLAYER_SAVE_FORMAT_STRING,
                        player.getMoney(),
                        player.getPosition().getX(),
                        player.getPosition().getY());
        assertIsEqualTo(lines.get(0), expectedLine);
    }

    private int assertItemLinesAreEqual(ArrayList<String> lines, Item item, int count, int currentLine) {
        String expectedLine = String.format(SaveWriter.ITEM_SAVE_FORMAT_STRING, item.toString(), count);

        assertIsEqualTo(lines.get(currentLine++), expectedLine);
        return currentLine;
    }

    private int assertBlockamonLinesAreEqual(ArrayList<String> lines, Blockamon blockamon, int position, int currentLine) {
        String expectedLine = String
                .format(SaveWriter.BLOCKAMON_SAVE_FORMAT_STRING,
                        blockamon.name(),
                        blockamon.elementType().toString(),
                        blockamon.status(),
                        blockamon.currentHp(),
                        blockamon.maxHp(),
                        blockamon.level(),
                        blockamon.currentExp(),
                        blockamon.neededExp(),
                        blockamon.totalAttack(),
                        "" + blockamon.isLead(),
                        position);

        assertIsEqualTo(lines.get(currentLine++), expectedLine);
        return currentLine;
    }

    private void assertIsEqualTo(Object actual, Object expected) {
        assertEquals(expected, actual);
    }

    private int calculateExpectedLineCount() {
        return 1 + (_player.getBlockamon().size()) + (_player.getItems().length);
    }

    protected void tearDown() throws Exception {
        File file = new File(TEST_FILE_NAME);
        if(file.exists()) {
            file.delete();
        }
        super.tearDown();
    }
}
