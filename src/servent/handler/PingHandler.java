package servent.handler;

import app.AppConfig;
import app.AppTool;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.PingMessage;
import servent.message.PongMessage;
import servent.message.util.MessageUtil;

import java.security.PublicKey;

public class PingHandler implements MessageHandler {
    private Message clientMessage;

    public PingHandler(Message clientMessage){
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.PING){
            try {

                PingMessage pingMessage = (PingMessage) clientMessage;
                if(pingMessage.getMessageText().equals("Ping")){
                    MessageUtil.sendMessage(new PongMessage(AppConfig.myServentInfo.getListenerPort(), clientMessage.getSenderPort(), clientMessage.getMessageText()));
                }else if(pingMessage.getMessageText().equals("IsOk?")){
                    MessageUtil.sendMessage(new PingMessage(AppConfig.myServentInfo.getListenerPort(), Short.parseShort(pingMessage.getPortForCheckup()), "SuspiciousAsk", "0"));
                    try {
                        Thread.sleep(3000 + AppConfig.WEAK_FAILURE_LIMIT);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    if(!AppConfig.pingingInfo.isSuspicious()){
                        MessageUtil.sendMessage(new PongMessage(AppConfig.myServentInfo.getListenerPort(), clientMessage.getSenderPort(), "Failed"));
                        AppConfig.pingingInfo.setSuspicious(false);
                    }
                }else {
                    MessageUtil.sendMessage(new PongMessage(AppConfig.myServentInfo.getListenerPort(), clientMessage.getSenderPort(), "SuspiciosTell"));
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }else{
            AppTool.timestampedErrorPrint("Ping Handler got something other than Ping Message.");
        }
    }
}
