package blockamon.io;

import blockamon.Extensions;
import blockamon.IPlayerCreator;
import blockamon.items.Item;
import blockamon.objects.Blockamon;
import blockamon.objects.ElementType;
import blockamon.objects.Player;

import java.io.*;
import java.util.*;

public class SaveLoader implements ISaveLoader {

    private IPlayerCreator _playerCreator;
    private IFileChooserHandler _fileChooserHandler;

    public SaveLoader(IFileChooserHandler fileChooserHandler, IPlayerCreator playerCreator) {
        _fileChooserHandler = fileChooserHandler;
        _playerCreator = playerCreator;
    }

    public Player LoadSave(Player player) throws IOException {
        File file = _fileChooserHandler.getLoadFile();
        if(file == null) {
            return player;
        }
        player.clear();
        List<String> fileLines = Extensions.readAllLines(file);
        if(fileLines.size() < 0) {
            return player;
        }

        LineStorage lineStorage = storeAllLines(fileLines);

        SetupPlayerInfo(lineStorage, player);
        SetupPlayerItems(lineStorage, player);
        SetupPlayerBlockamon(lineStorage, player);
        return player;
    }

    public Player LoadSave() throws IOException {
        return LoadSave(_playerCreator.createNewPlayer());
    }

    private LineStorage storeAllLines(List<String> lines) {
        LineStorage lineStorage = new LineStorage();
        for(String line : lines) {
            lineStorage.addLine(line);
        }
        return lineStorage;
    }

    private void SetupPlayerInfo(LineStorage lineStorage, Player player) {
        String playerLine = lineStorage.getPlayerLine();

        MapStore mapStore = MapStore.createMapStore(playerLine);

        double money = asDouble(mapStore.getValue("Money"));
        player.setMoney(money);

        int x = asInt(mapStore.getValue("PositionX"));
        int y = asInt(mapStore.getValue("PositionY"));
        player.setPosition(x, y);
    }

    private void SetupPlayerItems(LineStorage lineStorage, Player player) {
        List<String> itemLines = lineStorage.getItemLines();

        List<MapStore> mapStores = MapStore.createMapStores(itemLines);

        for(MapStore mapStore : mapStores) {
            Item item = Item.valueOf(mapStore.getValue("Name").replace("'", ""));
            int count = asInt(mapStore.getValue("Count"));
            player.addItems(item, count);
        }
    }

    private void SetupPlayerBlockamon(LineStorage lineStorage, Player player) {
        List<String> blockamonLines = lineStorage.getBlockamonLines();

        List<MapStore> mapStores = MapStore.createMapStores(blockamonLines);

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
        for(MapStore mapStore : mapStores) {
            ElementType type = ElementType
                    .valueOf(mapStore.getValue("Type"));
            Blockamon blockamon = new Blockamon(type);
            blockamon.name(mapStore.getValue("Name"));
            blockamon.status(mapStore.getValue("Status"));
            blockamon.maxHp(asDouble(mapStore.getValue("MaxHp")));
            blockamon.currentHp(asDouble(mapStore.getValue("CurrentHp")));
            blockamon.totalAttack(asDouble(mapStore.getValue("MaxAttack")));
            blockamon.currentAttack(blockamon.totalAttack());
            blockamon.level(asInt(mapStore.getValue("Level")));
            blockamon.currentExp(asInt(mapStore.getValue("CurrentExp")));
            blockamon.neededExp(asInt(mapStore.getValue("NeededExp")));
            blockamon.isLead(asBoolean(mapStore.getValue("IsLead")));
            player.addToParty(blockamon);
        }
    }

    private boolean asBoolean(String s) {
        return Boolean.parseBoolean(s);
    }

    private double asDouble(String s) {
        return Double.parseDouble(s);
    }

    private int asInt(String s) {
        return Integer.parseInt(s);
    }

    private class LineStorage {
        private String _playerLine;
        private List<String> _items;
        private List<String> _blockamon;

        public LineStorage() {
            _items = new ArrayList<String>();
            _blockamon = new ArrayList<String>();
        }

        public void addLine(String line) {
            if(line.startsWith("Player")) {
                _playerLine = line;
            }
            else if(line.startsWith("Item")) {
                _items.add(line);
            }
            else if(line.startsWith("Blockamon")) {
                _blockamon.add(line);
            }
        }

        public List<String> getItemLines() {
            return _items;
        }

        public List<String> getBlockamonLines() {
            return _blockamon;
        }

        public String getPlayerLine() {
            return _playerLine;
        }
    }
}
