package servent.handler;

import app.AppTool;
import mutex.MutexToken;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.TokenMessage;

public class TokenHandler implements MessageHandler{

    private Message clientMessage;

    public TokenHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.TOKEN){
            MutexToken.receiveToken();
        }else{
            AppTool.timestampedErrorPrint("Token Handler got something other than Token Message.");
        }
    }
}
