package app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppTool {

    /**
     * Print a servent.message to stdout with a timestamp
     * @param message servent.message to print
     */
    public static void timestampedStandardPrint(String message) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();

        System.out.println(timeFormat.format(now) + " - " + message);
    }

    /**
     * Print a servent.message to stderr with a timestamp
     * @param message servent.message to print
     */
    public static void timestampedErrorPrint(String message) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();

        System.err.println(timeFormat.format(now) + " - " + message);
    }

}
