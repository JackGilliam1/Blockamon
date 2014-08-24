package blockamon.io;

/**
 * User: Jack's Computer
 * Date: 8/24/2014
 * Time: 10:21 AM
 */
public class PrintManager {
    public static <T> IPrinter getPrinter(T type) {
        return new Printer<T>(type);
    }
}
