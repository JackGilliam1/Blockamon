package blockamon.io;

import blockamon.IPlayerCreator;
import blockamon.items.Item;
import blockamon.objects.Player;

import java.io.*;
import java.util.ArrayList;

/**
 * User: Jack's Computer
 * Date: 4/27/2014
 * Time: 1:47 PM
 */
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
            currentLine = SetupPlayerItems(fileLines, player, currentLine);
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
        while(currentLine < fileLines.size() && fileLines.get(currentLine++).contains("Item")) {
            String itemName = getStringValue(fileLines.get(currentLine++));
            Item item = Item.valueOf(itemName);
            int count = getIntValue(fileLines.get(currentLine++));
            for(int i = 0; i < count;i++) {
                player.addItem(item);
            }
        }
        return currentLine;
    }

    private int getIntValue(String line) {
        return Integer.parseInt(line.split(":")[1]);
    }

    private String getStringValue(String line) {
        return line.split(":")[1].replace("\'", "");
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
