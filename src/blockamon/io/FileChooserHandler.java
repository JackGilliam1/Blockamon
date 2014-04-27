package blockamon.io;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

/**
 * User: Jack's Computer
 * Date: 4/27/2014
 * Time: 10:59 AM
 */
public class FileChooserHandler extends Component implements IFileChooserHandler {
    private JFileChooser _fileChooser;

    public FileChooserHandler(FileFilter fileFilter) {
        _fileChooser = new JFileChooser();
        _fileChooser.setFileFilter(fileFilter);
    }

    /**
     * Allows the user to choose a file to load from
     * @return null if user chose cancel, else the file chosen
     */
    public File getLoadFile() {
        int actionCode = _fileChooser.showOpenDialog(this);
        if(actionCode == JFileChooser.APPROVE_OPTION) {
            return _fileChooser.getSelectedFile();
        }
        return null;
    }

    /**
     * Allows the user to choose a file to save to
     * @return null if user chose cancel, else the file chosen
     */
    public File getSaveFile() {
        int actionCode = _fileChooser.showSaveDialog(this);
        if(actionCode == JFileChooser.APPROVE_OPTION) {
            return _fileChooser.getSelectedFile();
        }
        return null;
    }
}
