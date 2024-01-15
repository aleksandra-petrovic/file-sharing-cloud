package servent.handler;

import app.AppConfig;
import app.AppTool;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.PullTellMessage;

public class PullTellHandler implements MessageHandler {

    private Message clientMessage;

    public PullTellHandler(Message clientMessage){
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {

        if(clientMessage.getMessageType() == MessageType.PULL_TELL){
            PullTellMessage pullTellMessage = (PullTellMessage) clientMessage;

            AppTool.timestampedStandardPrint("Pulled file " + pullTellMessage.getFileInfo().getPath());
            AppTool.timestampedErrorPrint(pullTellMessage.getFileInfo().getContent());

        }else{
            AppTool.timestampedErrorPrint("Pull Tell Handler got something other than Pull Tell Message.");
        }

    }
}
