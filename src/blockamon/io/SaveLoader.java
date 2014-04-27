package blockamon.io;

import blockamon.IPlayerCreator;
import blockamon.items.Item;
import blockamon.objects.Blockamon;
import blockamon.objects.ElementType;
import blockamon.objects.Player;

import java.io.*;
import java.util.ArrayList;

public class SaveLoader implements ISaveLoader {

    private IPlayerCreator _playerCreator;
    private IFileChooserHandler _fileChooserHandler;

    public SaveLoader(IFileChooserHandler fileChooserHandler, IPlayerCreator playerCreator) {
        _fileChooserHandler = fileChooserHandler;
        _playerCreator = playerCreator;
    }

    public Player LoadSave() throws IOException {
        Player player = _playerCreator.createNewPlayer();
        ArrayList<String> fileLines = readAllLines(_fileChooserHandler.getLoadFile());
        if(fileLines.size() > 0) {
            int currentLine = 0;
            if(fileLines.get(0).contains("Player")) {
                currentLine = 1;
            }
            currentLine = SetupPlayerInfo(fileLines, player, currentLine);
            int blockamonStart = -1;
            int itemsStart = -1;
            for(int i = 0; i < fileLines.size(); i++) {
                if(fileLines.get(i).equals("Item") && itemsStart == -1) {
                    itemsStart = i;
                }
                if(fileLines.get(i).equals("Blockamon") && blockamonStart == -1) {
                    blockamonStart = i;
                }
            }
            if(itemsStart != -1) {
                SetupPlayerItems(fileLines, player, itemsStart);
            }
            if(blockamonStart != -1) {
                SetupPlayerBlockamon(fileLines, player, blockamonStart);
            }
        }
        return player;
    }

    private int SetupPlayerInfo(ArrayList<String> fileLines, Player player, int currentLine) {
        if(fileLines.size() > 3) {
            double money = getDoubleValue(fileLines.get(currentLine++));
            player.setMoney(money);
            int x = getIntValue(fileLines.get(currentLine++));
            int y = getIntValue(fileLines.get(currentLine++));
            player.setPosition(x, y);
        }
        return currentLine;
    }

    private int SetupPlayerItems(ArrayList<String> fileLines, Player player, int currentLine) {
        while(currentLine < fileLines.size() && fileLines.get(currentLine).contains("Item")) {
            String itemName = getStringValue(fileLines.get(currentLine + 1));
            Item item = Item.valueOf(itemName);
            int count = getIntValue(fileLines.get(currentLine + 2));
            for(int i = 0; i < count;i++) {
                player.addItem(item);
            }
            currentLine += 3;
        }
        return currentLine;
    }

    private int SetupPlayerBlockamon(ArrayList<String> fileLines, Player player, int currentLine) {
        Blockamon blockamon;
        while(currentLine < fileLines.size() && fileLines.get(currentLine).contains("Blockamon")) {
            /**
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
             */
            String blockamonName = getStringValue(fileLines.get(currentLine + 1));
            ElementType type = ElementType.valueOf(getStringValue(fileLines.get(currentLine + 2)));
            blockamon = new Blockamon(type);
            blockamon.setName(blockamonName);
            String status = getStringValue(fileLines.get(currentLine + 3));
            blockamon.setStatus(status);
            int currentHealth = getIntValue(fileLines.get(currentLine + 4));
            blockamon.currentHitPoints(currentHealth);
            int maxHP = getIntValue(fileLines.get(currentLine + 5));
            blockamon.maxHP(maxHP);
            int level = getIntValue(fileLines.get(currentLine + 6));
            blockamon.setCurrentLevel(level);
            int currentExp = getIntValue(fileLines.get(currentLine + 7));
            blockamon.setExperience(currentExp);
            int expNeeded = getIntValue(fileLines.get(currentLine + 8));
            blockamon.setNeededExperience(expNeeded);
            double totalAttack = getDoubleValue(fileLines.get(currentLine + 9));
            blockamon.setTotalAttack(totalAttack);
            boolean isLead = getBooleanValue(fileLines.get(currentLine + 10));
            blockamon.isLead(isLead);
            if(isLead) {
                player.setLeadBlockamon(blockamon);
            }
            else {
                player.addToParty(blockamon);
            }
            currentLine += 12;
        }
        return currentLine;
    }

    private int getIntValue(String line) {
        return Integer.parseInt(line.split(":")[1]);
    }

    private String getStringValue(String line) {
        return line.split(":")[1].replace("\'", "");
    }

    private boolean getBooleanValue(String line) {
        return Boolean.parseBoolean(line.split(":")[1]);
    }

    private double getDoubleValue(String line) {
        return Double.parseDouble(line.split(":")[1]);
    }

    private ArrayList<String> readAllLines(File file) throws IOException {
        ArrayList<String> linesRead = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String lineRead = "";
            while(lineRead != null) {
                lineRead = bufferedReader.readLine();
                if(lineRead != null) {
                    linesRead.add(lineRead);
                }
            }
        }
        finally {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(fileReader != null) {
                fileReader.close();
            }
        }
        return linesRead;
    }
}
