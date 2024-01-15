package servent.message;

import app.file.FileInfo;

import java.io.File;

public class PullTellMessage extends BasicMessage{
    private static final long serialVersionUID = 38977534252127636L;

    private FileInfo fileInfo;

    public PullTellMessage(Short senderPort, Short receiverPort, FileInfo fileInfo) {
        super(MessageType.PULL_TELL, senderPort, receiverPort, "Hi. Here is the file you asked for.");
        this.fileInfo = fileInfo;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }
}
