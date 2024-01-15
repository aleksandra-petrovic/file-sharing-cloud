package servent;

import app.AppConfig;
import app.AppTool;
import app.Cancellable;
import servent.handler.*;
import servent.message.DeletedNodeUpdateMessage;
import servent.message.Message;
import servent.message.util.MessageUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleServentListener implements Runnable, Cancellable {
    private volatile boolean working = true;

    public SimpleServentListener() { }

    /*
     * Thread pool for executing the handlers. Each client will get it's own handler thread.
     */
    private final ExecutorService threadPool = Executors.newWorkStealingPool();

    @Override
    public void run() {
        ServerSocket listenerSocket = null;
        try {
            listenerSocket = new ServerSocket(AppConfig.myServentInfo.getListenerPort(), 100);
            /*
             * If there is no connection after 1s, wake up and see if we should terminate.
             */
            listenerSocket.setSoTimeout(1000);
        } catch (IOException e) {
            AppTool.timestampedErrorPrint("Couldn't open listener socket on: " + AppConfig.myServentInfo.getListenerPort());
            System.exit(0);
        }


        while (working) {
            try {
                Message clientMessage;

                Socket clientSocket = listenerSocket.accept();

                //GOT A MESSAGE! <3
                clientMessage = MessageUtil.readMessage(clientSocket);

                MessageHandler messageHandler = new NullHandler(clientMessage);


                /*
                 * Each message type has it's own handler.
                 * If we can get away with stateless handlers, we will,
                 * because that way is much simpler and less error prone.
                 */
                switch (clientMessage.getMessageType()) {
                    case HI:
                        messageHandler = new HiHandler(clientMessage);
                        break;
                    case WELCOME:
                        messageHandler = new WelcomeHandler(clientMessage);
                        break;
                    case NEW_NODE_UPDATE:
                        messageHandler = new NewNodeUpdateHandler(clientMessage);
                        break;
                    case DELETED_NODE_UPDATE:
                        messageHandler = new DeletedNodeUpdateHandler(clientMessage);
                        break;
                    case PING:
                        messageHandler = new PingHandler(clientMessage);
                        break;
                    case PONG:
                        messageHandler = new PongHandler(clientMessage);
                        break;
                    case TOKEN:
                        messageHandler = new TokenHandler(clientMessage);
                        break;
                    case WANT_TOKEN:
                        messageHandler = new WantTokenHandler(clientMessage);
                        break;
                    case BACKUP:
                        messageHandler = new BackupHandler(clientMessage);
                        break;
                    case PULL_ASK:
                        messageHandler = new PullAskHandler(clientMessage);
                        break;
                    case PULL_TELL:
                        messageHandler = new PullTellHandler(clientMessage);
                        break;
                }

                threadPool.submit(messageHandler);
            } catch (SocketTimeoutException timeoutEx) {
                //Uncomment the next line to see that we are waking up every second.
//				AppConfig.timedStandardPrint("Waiting...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        this.working = false;
    }
}
