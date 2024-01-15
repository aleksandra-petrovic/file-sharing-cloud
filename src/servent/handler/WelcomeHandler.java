package servent.handler;

import app.AppConfig;
import app.AppTool;
import app.BootstrapServer;
import app.ServentInfo;
import mutex.MutexToken;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.WelcomeMessage;

import java.util.*;

public class WelcomeHandler implements MessageHandler{
    private Message clientMessage;

    public WelcomeHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.WELCOME){
            WelcomeMessage welcomeMessage = (WelcomeMessage) clientMessage;

            boolean put = true;
            AppConfig.allServents = welcomeMessage.getAllServents();
            for (ServentInfo serventInfo : AppConfig.allServents){
                if(serventInfo.getPhysicalId() == AppConfig.myServentInfo.getPhysicalId()){
                    put = false;
                }
            }

            if(put) AppConfig.allServents.add(AppConfig.myServentInfo);

            Calculator.calculateAndSet();

            MutexToken.unlock();

        }else{
            AppTool.timestampedErrorPrint("Welcome Handler got something other than a Welcome message.");
        }

    }
}
