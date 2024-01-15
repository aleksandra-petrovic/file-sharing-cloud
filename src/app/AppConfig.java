package app;

import app.checkup.PingingInfo;
import app.file.Storage;
import com.sun.org.apache.xerces.internal.impl.xs.util.ShortListImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.NetworkChannel;
import java.sql.Statement;
import java.util.*;

/**
 * This class contains all the global application configuration stuff.
 * @author bmilojkovic
 *
 */
public class AppConfig {

    /**
     * Convenience access for this servent's information
     */
    public static ServentInfo myServentInfo;
    public static Set<ServentInfo> allServents = new HashSet<>();
    public static String ROOT_DIR;
    public static String BOOTSTRAP_IP_ADDRESS;
    public static short BOOTSTRAP_PORT;
    public static int WEAK_FAILURE_LIMIT;
    public static int STRONG_FAILURE_LIMIT;
    public static PingingInfo pingingInfo = new PingingInfo();
    public static Storage storage = new Storage();
    public static Queue<Short> tokenQueue;



    public static void readConfig(String configName, int serventId){
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(new File(configName)));
        } catch (IOException e) {
            AppTool.timestampedErrorPrint("Couldn't open properties file. Exiting...");
            System.exit(0);
        }

        try {
            ROOT_DIR = properties.getProperty("root.dir");
        } catch (NumberFormatException e) {
            AppTool.timestampedErrorPrint("Problem reading root directory. Exiting...");
            System.exit(0);
        }

        try {
            BOOTSTRAP_IP_ADDRESS = properties.getProperty("bootstrap.ip");
        } catch (NumberFormatException e) {
            AppTool.timestampedErrorPrint("Problem reading bootstrap node ip address. Exiting...");
            System.exit(0);
        }

        try {
            BOOTSTRAP_PORT = Short.parseShort(properties.getProperty("bootstrap.port"));
        } catch (NumberFormatException e) {
            AppTool.timestampedErrorPrint("Problem reading bootstrap node port. Exiting...");
            System.exit(0);
        }

        try {
            WEAK_FAILURE_LIMIT = Integer.parseInt(properties.getProperty("wfl"));
        } catch (NumberFormatException e) {
            AppTool.timestampedErrorPrint("Problem reading weak failure limit. Exiting...");
            System.exit(0);
        }

        try {
            STRONG_FAILURE_LIMIT = Integer.parseInt(properties.getProperty("sfl"));
        } catch (NumberFormatException e) {
            AppTool.timestampedErrorPrint("Problem reading strong failure limit. Exiting...");
            System.exit(0);
        }


        if(serventId >= 0) {
            String serventAddress = properties.getProperty("servent" + serventId + ".address");
            short serventPort = Short.parseShort(properties.getProperty("servent" + serventId + ".port"));

            myServentInfo = new ServentInfo(serventAddress, serventId, serventPort, new HashSet<>());
            allServents.add(myServentInfo);
        }
    }


}
