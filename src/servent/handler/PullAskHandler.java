package servent.handler;

import app.AppConfig;
import app.AppTool;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.PullAskMessage;
import servent.message.PullTellMessage;
import servent.message.util.MessageUtil;

public class PullAskHandler implements MessageHandler{

    private Message clientMessage;

    public PullAskHandler(Message clientMessage){
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.PULL_ASK){
            PullAskMessage pullAskMessage = (PullAskMessage) clientMessage;

            if(AppConfig.storage.getMyFiles().containsKey(pullAskMessage.getFilePath())){
                MessageUtil.sendMessage(new PullTellMessage(AppConfig.myServentInfo.getListenerPort(),
                                                            pullAskMessage.getSenderPort(), AppConfig.storage.getMyFiles().get(pullAskMessage.getFilePath())));
            }else{
                MessageUtil.sendMessage(new PullAskMessage(pullAskMessage.getSenderPort(), AppConfig.pingingInfo.getSecondSuccessor(),
                                                                pullAskMessage.getFilePath()));
            }

        }else{
            AppTool.timestampedErrorPrint("Pull Ask Handler got something other than Pull Ask Message.");
        }
    }
}
