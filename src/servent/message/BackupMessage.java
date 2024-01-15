package servent.message;

import app.AppConfig;
import app.ServentInfo;
import app.file.FileInfo;

import java.util.List;
import java.util.Map;

public class BackupMessage extends BasicMessage{

    private static final long serialVersionUID = 3899831109242127636L;

    private Map<String, FileInfo> backupFiles;

    public BackupMessage(Short senderPort, Short receiverPort, Map<String, FileInfo> backupFiles) {
        super(MessageType.BACKUP, senderPort, receiverPort, "Hi. Im giving you my files to backup.");
        this.backupFiles = backupFiles;
    }

    public Map<String, FileInfo> getBackupFiles() {
        return backupFiles;
    }
}
