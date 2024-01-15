package servent.handler;

import app.AppConfig;
import app.AppTool;
import app.ServentInfo;
import servent.message.DeletedNodeUpdateMessage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.NewNodeUpdateMessage;
import servent.message.util.MessageUtil;

import java.util.*;

public class NewNodeUpdateHandler implements MessageHandler{

    private Message clientMessage;

    public NewNodeUpdateHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {

        if(clientMessage.getMessageType() == MessageType.NEW_NODE_UPDATE){
            NewNodeUpdateMessage newNodeUpdateMessage = (NewNodeUpdateMessage) clientMessage;
            boolean put = true;

            if(AppConfig.allServents != null) {
                for (ServentInfo serventInfo : AppConfig.allServents) {
                    if (serventInfo.getPhysicalId() == newNodeUpdateMessage.getNewNodeServentInfo().getPhysicalId()) {
                        put = false;
                    }
                }
            }
            if(put) {
                AppConfig.allServents.add(newNodeUpdateMessage.getNewNodeServentInfo());
            }

           Calculator.calculateAndSet();

        }else {
            AppTool.timestampedErrorPrint("New Node Update Handler got a message that is not New Node Update Message.");
        }

    }

}
