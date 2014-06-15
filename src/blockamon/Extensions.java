package blockamon;

import blockamon.objects.data.AppearanceData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 2:26 PM
 */
public final class Extensions {
    private Extensions() {
    }

    public static <T> ArrayList<T> toArrayList(T... values) {
        ArrayList<T> savedValues = new ArrayList<T>();
        for(T value : values)
        {
            savedValues.add(value);
        }
        return savedValues;
    }

    public static <T> T as(Object obj, Class<T> tClass) {
        return tClass.isInstance(obj) ? (T) obj : null;
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
