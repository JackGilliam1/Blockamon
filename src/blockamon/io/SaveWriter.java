package blockamon.io;

import blockamon.items.Item;
import blockamon.objects.Blockamon;
import blockamon.objects.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveWriter implements ISaveWriter {
    private IFileChooserHandler _fileChooserHandler;

    public SaveWriter(IFileChooserHandler fileChooserHandler) {
        _fileChooserHandler = fileChooserHandler;
    }

    public void SaveGame(Player player) throws IOException {
        File file = _fileChooserHandler.getSaveFile();
        if (file != null) {
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
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    /**
     * Format (without the empty lines):
     * Player
     * Money:2500.0
     * PositionX:8
     * PositionY:10
     * <p/>
     * Item
     * Name:'Some Item'
     * Count:10
     * <p/>
     * Item
     * Name:'Some Other Item'
     * Count:20
     * <p/>
     * Blockamon
     * Name:'Some Blockamon Name'
     * Type:'Fire'
     * Status:'FAINTED'
     * CurrentHealth:0
     * TotalHealth:800
     * Level:14
     * CurrentEXP:20
     * EXPNeeded:200
     * TotalAttack:20
     * IsLead:true
     * Position:0
     * <p/>
     * Blockamon
     * Name:'Some Other Name'
     * Type:'Ice'
     * Status:'NONE'
     * CurrentHealth:88
     * TotalHealth:800
     * Level:17
     * CurrentEXP:20
     * EXPNeeded:200
     * TotalAttack:20
     * IsLead:false
     * Position:1
     */
    private String BuildSaveFile(Player player) {
        StringBuilder builder = new StringBuilder();
        appendLn(builder, "Player");
        appendLn(builder, "Money:" + player.getMoney());
        appendLn(builder, "PositionX:" + player.getX());
        appendLn(builder, "PositionY:" + player.getY());
        BuildItemString(builder, player);
        BuildBlockamonString(builder, player.getBlockamon());
        return builder.toString();
    }

    private String NEW_LINE = System.getProperty("line.separator");

    private void appendLn(StringBuilder builder, String data) {
        builder.append(data);
        builder.append(NEW_LINE);
    }

    private void BuildItemString(StringBuilder builder, Player player) {
        Item[] items = player.getItems();
        for (Item item : items) {
            appendLn(builder, "Item");
            appendLn(builder, "Name:'" + item.toString() + "'");
            appendLn(builder, "Count:" + player.getItemCount(item));
        }
    }

    private void BuildBlockamonString(StringBuilder builder, ArrayList<Blockamon> blockamons) {
        for (int i = 0; i < blockamons.size(); i++) {
            Blockamon blockamon = blockamons.get(i);
            appendLn(builder, "Blockamon");
            appendLn(builder, "Name:'" + blockamon.getName() + "'");
            appendLn(builder, "Type:'" + blockamon.getElementType() + "'");
            appendLn(builder, "Status:'" + blockamon.getStatus() + "'");
            appendLn(builder, "CurrentHealth:" + blockamon.currentHitPoints());
            appendLn(builder, "TotalHealth:" + blockamon.maxHP());
            appendLn(builder, "Level:" + blockamon.getCurrentLevel());
            appendLn(builder, "CurrentEXP:" + blockamon.getExperience());
            appendLn(builder, "EXPNeeded:" + blockamon.getNeededExperience());
            appendLn(builder, "TotalAttack:" + blockamon.getTotalAttack());
            appendLn(builder, "IsLead:" + blockamon.isLead());
            appendLn(builder, "Position:" + i);
        }
    }
}
