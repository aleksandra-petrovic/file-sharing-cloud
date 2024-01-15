package app;

import mutex.MutexToken;
import servent.message.HiMessage;
import servent.message.WelcomeMessage;
import servent.message.util.MessageUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServentInitializer implements Runnable {

    private Short getSomeServentPort() {
        //vrednost iz konfiguracione datoteke tj 1000
        int bsPort = AppConfig.BOOTSTRAP_PORT;

        //inicijalno setujemo gresku, bice overwrite-ovana dalje
        short retVal = -2;

        try {
            //otvara se socket za localhost 2000 tj bootstrap
            Socket bsSocket = new Socket("localhost", bsPort);

            //napisemo svoj info u output stream tj javimo se bootstrap cvoru
            PrintWriter bsWriter = new PrintWriter(bsSocket.getOutputStream());
            bsWriter.write("Hail\n" + AppConfig.myServentInfo.getListenerPort() + "\n");
            bsWriter.flush();

            //skeniramo odgovor i iz input streama uzimamo port cvora kome treba da se javimo od bootstrap cvora
            Scanner bsScanner = new Scanner(bsSocket.getInputStream());
            retVal = bsScanner.nextShort();

            bsSocket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retVal;
    }

    @Override
    public void run() {
        Short someServentPort = getSomeServentPort();

        if (someServentPort == -2) {
            AppTool.timestampedErrorPrint("Error in contacting bootstrap. Exiting...");
            System.exit(0);
        }
        if (someServentPort == -1) { //bootstrap gave us -1 -> we are first
            AppTool.timestampedStandardPrint("First node in system.");
            MutexToken.init();

        } else { //bootstrap gave us something else - let that node tell our successor that we are here
            //dobili smo cvor od BS cvora i sada saljemo poruku cvoru koji je na tom portu da treba da nas ubaci u sistem
            MessageUtil.sendMessage(new HiMessage(AppConfig.myServentInfo.getListenerPort(), someServentPort, AppConfig.myServentInfo));

        }
    }
}
