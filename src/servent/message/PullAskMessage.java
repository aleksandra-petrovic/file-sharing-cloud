package servent.message;

public class PullAskMessage extends BasicMessage{
    private static final long serialVersionUID = 38977534252127636L;

    private String filePath;

    public PullAskMessage(Short senderPort, Short receiverPort, String filePath) {
        super(MessageType.PULL_ASK, senderPort, receiverPort, "Hi. Im searching for a file.");
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
