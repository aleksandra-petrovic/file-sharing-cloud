package servent.message;

import app.AppTool;
import app.ServentInfo;

public class DeletedNodeUpdateMessage extends BasicMessage{

    private static final long serialVersionUID = 3899837282218127636L;

    private ServentInfo deletedNodeServentInfo;

    public DeletedNodeUpdateMessage(Short senderPort, Short receiverPort, ServentInfo deletedNodeServentInfo) {
        super(MessageType.DELETED_NODE_UPDATE, senderPort, receiverPort, "Hi. One node left our system, update accordingly.");
        this.deletedNodeServentInfo = deletedNodeServentInfo;
    }

    public ServentInfo getDeletedNodeServentInfo() {
        return deletedNodeServentInfo;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + deletedNodeServentInfo.getListenerPort() + "] ";
    }

}
