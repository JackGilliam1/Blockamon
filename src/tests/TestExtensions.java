package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * User: Jack's Computer
 * Date: 4/27/2014
 * Time: 11:43 AM
 */
public class TestExtensions {
    private TestExtensions()
    {
    }

    public static ArrayList<String> readAllLines(File file) throws IOException {
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
