package blockamon_tests.mocks;

import blockamon.io.IFileChooserHandler;

import java.io.File;
import java.io.IOException;

public class MockFileChooserHandler implements IFileChooserHandler {
    private String _fileName;

    public MockFileChooserHandler(String fileName) {
        _fileName = fileName;
    }

    public File getLoadFile() {
        File file = new File(_fileName);
        if(!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if(!created){
                    System.err.println("File was not created successfully! for reasons unknown");
                }
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return file;
    }

    public File getSaveFile() {
        File file = new File(_fileName);
        if(file.exists()) {
            file.delete();
        }
        return file;
    }
}
