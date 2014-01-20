package blockamon;

import java.util.ArrayList;

/**
 * User: Jack's Computer
 * Date: 1/5/14
 * Time: 2:26 PM
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

    public static <T> T as(Object obj, Class<T> tClass) {
        return tClass.isInstance(obj) ? (T) obj : null;
    }
}
