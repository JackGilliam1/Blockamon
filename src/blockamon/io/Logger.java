package blockamon.io;

import java.util.Calendar;

/**
 * User: Jack's Computer
 * Date: 6/15/2014
 * Time: 12:31 PM
 */
public class Logger implements ILogger {
    private static final String logPath = System.getProperty("user.home") + System.getProperty("file.separator") + "logs";

    public void log(String text) {
        Calendar now = Calendar.getInstance();
        //TODO: Write this Logger
    }
}
