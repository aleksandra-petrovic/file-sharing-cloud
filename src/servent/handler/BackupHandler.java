package servent.handler;

import app.AppConfig;
import app.AppTool;
import app.file.FileInfo;
import servent.message.BackupMessage;
import servent.message.Message;
import servent.message.MessageType;

public class BackupHandler implements MessageHandler{

    private Message clientMessage;

    public BackupHandler(Message clientMessage){ this.clientMessage = clientMessage; }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.BACKUP){
            BackupMessage backupMessage = (BackupMessage) clientMessage;

            AppConfig.storage.addAllToStorage(backupMessage.getBackupFiles(), backupMessage.getSenderPort());

        }else{
            AppTool.timestampedErrorPrint("Backup Handler got something other than Backup Message");
        }
    }
}
