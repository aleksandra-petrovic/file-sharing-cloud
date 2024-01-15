package servent.message;

import app.ServentInfo;

public class NewNodeUpdateMessage extends BasicMessage{

    private static final long serialVersionUID = 3899837288998127636L;

    private ServentInfo newNodeServentInfo;

    public NewNodeUpdateMessage(Short senderPort, Short receiverPort, ServentInfo newNodeServentInfo) {
        super(MessageType.NEW_NODE_UPDATE, senderPort, receiverPort, "Hi. We have a new node in our system, update accordingly.");
        this.newNodeServentInfo = newNodeServentInfo;
    }

    public ServentInfo getNewNodeServentInfo() {
        return newNodeServentInfo;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + newNodeServentInfo.getListenerPort() + "] ";
    }
}
