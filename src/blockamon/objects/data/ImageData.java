package blockamon.objects.data;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 12:46 PM
 */
public class ImageData {
    private static final String _resourcesFolderName = "../../../resources";
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
            this.setFullPath();
        }
        return _fullPath;
    }

    public String getFullFilePath() {
        return getFullPath() + "/" + _imageName + ".png";
    }

    public String getImageName() {
        return _imageName;
    }
    public void setImageName(String name) {
        _imageName = name;
    }

    private void setFullPath() {
        _fullPath = getResourcesFolder();
        String folderName = getFolderName();
        if(folderName.startsWith("/")) {
            _fullPath += folderName;
        }
        else {
            _fullPath += "/" + folderName;
        }
    }
}
