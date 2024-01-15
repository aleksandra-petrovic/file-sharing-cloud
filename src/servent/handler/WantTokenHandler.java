package servent.handler;

import app.AppConfig;
import app.AppTool;
import mutex.MutexToken;
import servent.message.Message;
import servent.message.MessageType;

public class WantTokenHandler implements MessageHandler{
    private Message clientMessage;

    public WantTokenHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.WANT_TOKEN){
            AppConfig.tokenQueue.add(clientMessage.getSenderPort());
        }else{
            AppTool.timestampedErrorPrint("Want Token Handler got something other than Want Token Message.");
        }
    }
}
