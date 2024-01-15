package app;

import app.checkup.PingingThread;
import cli.CLIParser;
import servent.SimpleServentListener;
import servent.message.HiMessage;
import servent.message.PingMessage;
import servent.message.util.MessageUtil;

public class ServentMain {

    private static boolean bootstrap;

    /**
     * Command line arguments are:
     * 0 - path to servent list file
     * 1 - this servent's id
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            AppTool.timestampedErrorPrint("Please provide servent list file and id of this servent.");
        }

        int serventId = Integer.parseInt(args[1]);
        String configName = args[0];

        AppConfig.readConfig(configName, serventId);

        AppTool.timestampedStandardPrint("Starting servent " + AppConfig.myServentInfo);

        PingingThread pinging = new PingingThread();
        Thread pingingThread = new Thread(pinging);
        pingingThread.start();

        SimpleServentListener simpleListener = new SimpleServentListener();
        Thread listenerThread = new Thread(simpleListener);
        listenerThread.start();

        CLIParser cliParser = new CLIParser();
        Thread cliThread = new Thread(cliParser);
        cliThread.start();

        ServentInitializer serventInitializer = new ServentInitializer();
        Thread initializerThread = new Thread(serventInitializer);
        initializerThread.start();

    }
}
