package blockamon.io;

import blockamon.objects.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveWriter implements ISaveWriter
{
    private IFileChooserHandler _fileChooserHandler;

    public SaveWriter(IFileChooserHandler fileChooserHandler)
    {
        _fileChooserHandler = fileChooserHandler;
    }

    public void SaveGame(Player player) throws IOException {
        File file = _fileChooserHandler.getSaveFile();
        if(file != null) {
            String saveData = BuildSaveFile(player);
            WriteSaveData(file, saveData);
        }
    }

    private void WriteSaveData(File file, String data) throws IOException {
        BufferedWriter writer = null;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);
            writer.write(data);
        }
        finally{
            if(writer != null) {
                writer.close();
            }
            if(fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    /**
     * Things to save:
     * Player:
     *  Items:
     *      Type, Count
     *  Money
     *  Position:
     *      X, Y
     *  Blockamon:
     *      Type, Status, CurrentHealth, TotalHealth, Level, IsLead
     *  Blockamon Team:
     *      Order
     */

    /**
     * Format (without the empty lines):
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
    private String BuildSaveFile(Player player) {
        StringBuilder builder = new StringBuilder();
        appendLn(builder, "Player");
        appendLn(builder, "Money:" + player.getMoney());
        appendLn(builder, "Position:" + player.getPosition().getPositionString());
        //TODO: Items
        //TODO: Blockamon
        return builder.toString();
    }

    private String NEW_LINE = System.getProperty("line.separator");

    private void appendLn(StringBuilder builder, String data) {
        builder.append(data);
        builder.append(NEW_LINE);
    }
}
