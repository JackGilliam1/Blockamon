package blockamon.io;

/**
 * User: Jack's Computer
 * Date: 8/24/2014
 * Time: 10:22 AM
 */
public class Printer<T> implements IPrinter {

    public static boolean EnableLogging = true;

    private static final String FORMAT = "%s: %s";
    private T _type;

    public Printer(T type) {
        _type = type;
    }

    public void err(String message) {
        if(EnableLogging) {
            System.err.printf(FORMAT, _type.getClass().toString(), message);
            System.err.println();
        }
    }

    public void out(String message) {
        if(EnableLogging) {
            System.out.printf(FORMAT, _type.getClass().toString(), message);
            System.out.println();
        }
    }
}
