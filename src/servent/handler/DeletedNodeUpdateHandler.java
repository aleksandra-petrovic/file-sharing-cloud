package servent.handler;

import app.AppConfig;
import app.AppTool;
import app.ServentInfo;
import servent.message.DeletedNodeUpdateMessage;
import servent.message.Message;
import servent.message.MessageType;

public class DeletedNodeUpdateHandler implements MessageHandler{

    private Message clientMessage;

    public DeletedNodeUpdateHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {

        if(clientMessage.getMessageType() == MessageType.DELETED_NODE_UPDATE){
            DeletedNodeUpdateMessage deletedNodeUpdateMessage = (DeletedNodeUpdateMessage) clientMessage;

            for(ServentInfo serventInfo : AppConfig.allServents){
                if(serventInfo.getPhysicalId() == deletedNodeUpdateMessage.getDeletedNodeServentInfo().getPhysicalId()){
                    AppConfig.allServents.remove(serventInfo);
                }
            }

            Calculator.calculateAndSet();

        }else {
            AppTool.timestampedErrorPrint("New Node Update Handler got a message that is not New Node Update Message.");
        }

    }

}
