package servent.handler;

import app.AppConfig;
import app.AppTool;
import mutex.MutexToken;
import servent.message.*;
import servent.message.util.MessageUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HiHandler implements MessageHandler{
    private Message clientMessage;

    public HiHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {

        if(clientMessage.getMessageType() == MessageType.HI) {

            if (clientMessage.getReceiverPort() == AppConfig.BOOTSTRAP_PORT) {
                //you are the first node that wants to join the system, go ahead, welcome
                MessageUtil.sendMessage(new WelcomeMessage(AppConfig.BOOTSTRAP_PORT, clientMessage.getSenderPort()));
            } else {

                HiMessage hiMessage = (HiMessage) clientMessage;

                MutexToken.lock();

                MessageUtil.sendMessage(new WelcomeMessage(clientMessage.getReceiverPort(), clientMessage.getSenderPort()));

                for (Short neighbor : AppConfig.myServentInfo.getNeighbors()){
                    MessageUtil.sendMessage(new NewNodeUpdateMessage(AppConfig.myServentInfo.getListenerPort(), neighbor, hiMessage.getNewNodeServentInfo()));
                }
                MessageUtil.sendMessage(new NewNodeUpdateMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.myServentInfo.getListenerPort(), hiMessage.getNewNodeServentInfo()));

            }
        }else{
            AppTool.timestampedErrorPrint("Hi Handler got a message that is not a Hi Message.");
        }

    }
}
