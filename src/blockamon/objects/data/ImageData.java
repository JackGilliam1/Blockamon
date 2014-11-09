package blockamon.objects.data;

import java.nio.file.Paths;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 12:46 PM
 */
public class ImageData {
    private static final String _resourcesFolderName =
            Paths.get("..", "..", "..", "blockamon", "resources").toString();
    private String _subFolderName;
    private String _fullPath;
    private String _imageName;

    public ImageData(String folder){
        this(folder, "none");
    }

    public ImageData(String folder, String imageName) {
        _subFolderName = folder;
        _imageName = imageName;
    }

    public String getFolderName() {
        return _subFolderName;
    }

    public String getResourcesFolder() {
        return _resourcesFolderName;
    }

    public String getFullPath() {
        if(_fullPath == null) {
            _fullPath = Paths.get(getResourcesFolder(), getFolderName()).toString();
        }
        return _fullPath;
    }

    public String getFullFilePath() {
        return Paths.get(getFullPath(), _imageName + ".png").toString();
    }

    public String getImageName() {
        return _imageName;
    }
    public void setImageName(String name) {
        _imageName = name;
    }
}
