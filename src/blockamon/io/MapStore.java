package blockamon.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* User: Jack's Computer
* Date: 5/3/2014
* Time: 12:13 PM
*/
public class MapStore {
    private Map<String, String> _dataMap;

    public MapStore() {
        _dataMap = new HashMap<String, String>();
    }

    public void MapData(String[] dataItems) {
        for(String dataItem : dataItems) {
            String[] keyValue = dataItem.split(":");
            if(keyValue.length != 2) {
                continue;
            }
            String key = keyValue[0];
            String value = keyValue[1];
            if(_dataMap.containsKey(key)) {
                System.err.println(String
                        .format("Key:%s already in the dataMap with value:%s", key, value));
            }
            _dataMap.put(key, value);
        }
    }

    public String getValue(String key) {
        if(_dataMap.containsKey(key)) {
            return _dataMap.get(key);
        }
        return null;
    }

    public static List<MapStore> createMapStores(List<String> lines) {
        List<MapStore> mapStores = new ArrayList<MapStore>();
        for(String line : lines) {
            MapStore mapStore = createMapStore(line);
            mapStores.add(mapStore);
        }
        return mapStores;
    }

    public static MapStore createMapStore(String line) {
        MapStore mapStore = new MapStore();
        mapStore.MapData(line.split(","));
        return mapStore;
    }
}
