package servent.message.util;

import app.AppConfig;
import app.AppTool;
import servent.message.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This worker sends a servent.message asynchronously. Doing this in a separate thread
 * has the added benefit of being able to delay without blocking main or somesuch.
 *
 * @author bmilojkovic
 *
 */
public class DelayedMessageSender implements Runnable {
    private Message messageToSend;

    public DelayedMessageSender(Message messageToSend) {
        this.messageToSend = messageToSend;
    }

    public void run() {
        /*
         * A random sleep before sending.
         * It is important to take regular naps for health reasons.
         */
        try {
            Thread.sleep((long)(Math.random() * 1000) + 500);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        if (MessageUtil.MESSAGE_UTIL_PRINTING) {
            AppTool.timestampedStandardPrint("Sending message " + messageToSend);
        }

        try {
            Socket sendSocket = new Socket(messageToSend.getReceiverIpAddress(), messageToSend.getReceiverPort());

            ObjectOutputStream oos = new ObjectOutputStream(sendSocket.getOutputStream());
            oos.writeObject(messageToSend);
            oos.flush();

            sendSocket.close();
        } catch (IOException e) {
            AppTool.timestampedErrorPrint("Couldn't send message: " + messageToSend.toString());
        }
    }
}
