package servent.message;

import app.ServentInfo;

public class HiMessage extends BasicMessage{
    private static final long serialVersionUID = 3899837286642127636L;

    private ServentInfo newNodeServentInfo;

    public HiMessage(Short senderPort, Short receiverPort, ServentInfo newNodeServentInfo) {
        super(MessageType.HI, senderPort, receiverPort, "Hi. I want to be included in the system.");
        this.newNodeServentInfo = newNodeServentInfo;
    }

    public ServentInfo getNewNodeServentInfo() {
        return newNodeServentInfo;
    }
}
