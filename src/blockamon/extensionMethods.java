package blockamon;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExtensionMethods {
    public static <T> ArrayList<T> toArrayList(T... values) {
        ArrayList<T> savedValues = new ArrayList<T>();
        for(T value : values)
        {
            savedValues.add(value);
        }
        return savedValues;
    }
}
