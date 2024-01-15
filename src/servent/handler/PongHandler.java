package servent.handler;

import app.AppConfig;
import app.AppTool;
import app.ServentInfo;
import servent.message.DeletedNodeUpdateMessage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.PongMessage;
import servent.message.util.MessageUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PongHandler implements MessageHandler{
    private Message clientMessage;

    public PongHandler(Message clientMessage){
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.PONG){
            PongMessage pongMessage = (PongMessage) clientMessage;
            if(pongMessage.getMessageText().equals("Ping")) {
                AppConfig.pingingInfo.setTotalPongs(AppConfig.pingingInfo.getTotalPongs() + 1);
            } else if(pongMessage.getMessageText().equals("Failed")){
                try {
                    Thread.sleep(AppConfig.STRONG_FAILURE_LIMIT - AppConfig.WEAK_FAILURE_LIMIT - 3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                AppTool.timestampedStandardPrint("My neighbor shut down unexpectedly.");
                List<ServentInfo> allServents = new ArrayList<>(AppConfig.allServents);
                ServentInfo toBeRemoved = allServents.get(allServents.indexOf(new ServentInfo(AppConfig.pingingInfo.getNodeToPing())));
                allServents.remove(toBeRemoved);

                Calculator.calculateAndSet();

//                for(Short serventPort : AppConfig.myServentInfo.getNeighbors()) {
//                    MessageUtil.sendMessage(new DeletedNodeUpdateMessage(AppConfig.myServentInfo.getListenerPort(), serventPort));
//                }

                AppConfig.pingingInfo.setTotalPongs(AppConfig.pingingInfo.getTotalPongs() + 1);
                AppConfig.pingingInfo.setDoneChecking(true);

            }else{
                //suspicious response
                AppConfig.pingingInfo.setSuspicious(true);
            }
        }else{
            AppTool.timestampedErrorPrint("Pong Handler got something other than Pong Message.");
        }
    }
}
