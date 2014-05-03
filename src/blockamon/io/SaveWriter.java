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
    public final static String PLAYER_SAVE_FORMAT_STRING = "Player,Money:%f,PositionX:%d,PositionY:%d";
    public final static String ITEM_SAVE_FORMAT_STRING = "Item,Name:%s,Count:%d";
    public final static String BLOCKAMON_SAVE_FORMAT_STRING = "Blockamon" +
            ",Name:%s,Type:%s" +
            ",Status:%s,CurrentHp:%f" +
            ",MaxHp:%f,Level:%d,CurrentExp:%d" +
            ",NeededExp:%d,MaxAttack:%f,IsLead:%s,Position:%d";

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
     * Format:
     * Player,Money:2500.0.PositionX:8,PositionY:10
     * Item,Name:'Item 1',Count:10
     * Item,Name:'Some Other Item',Count:20
     * Blockamon,Name:'Some Blockamon Name',Type:'Fire',Status:'FAINTED',CurrentHealth:0,TotalHealth:800,Level:14,CurrentEXP:20,EXPNeeded:200,TotalAttack:18,IsLead:true,Position:0
     * Blockamon,Name:'Some Other Name',Type:'Ice',Status:'NONE',CurrentHealth:88,TotalHealth:800,Level:17,CurrentEXP:20,EXPNeeded:200,TotalAttack:18,IsLead:false,Position:1
     */
    private String BuildSaveFile(Player player) {
        StringBuilder builder = new StringBuilder();
        BuildPlayerString(builder, player);
        BuildItemString(builder, player);
        BuildBlockamonString(builder, player.getBlockamon());
        return builder.toString();
    }

    private String NEW_LINE = System.getProperty("line.separator");

    private void appendLn(StringBuilder builder, String data) {
        builder.append(data);
        builder.append(NEW_LINE);
    }

    private void BuildPlayerString(StringBuilder builder, Player player) {
        appendLn(builder,
                String.format(SaveWriter.PLAYER_SAVE_FORMAT_STRING,
                        player.getMoney(),
                        player.getPosition().getX(),
                        player.getPosition().getY()));
    }

    private void BuildItemString(StringBuilder builder, Player player) {
        Item[] items = player.getItems();
        for (Item item : items) {
            appendLn(builder, String.format(SaveWriter.ITEM_SAVE_FORMAT_STRING, item.toString(), player.getItemCount(item)));
        }
    }

    private void BuildBlockamonString(StringBuilder builder, ArrayList<Blockamon> blockamons) {
        for (int i = 0; i < blockamons.size(); i++) {
            Blockamon blockamon = blockamons.get(i);
            appendLn(builder, String.format(SaveWriter.BLOCKAMON_SAVE_FORMAT_STRING,
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
                    i));
        }
    }
}
