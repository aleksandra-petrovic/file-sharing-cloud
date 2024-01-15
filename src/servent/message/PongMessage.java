package servent.message;

import app.ServentInfo;

public class PongMessage extends BasicMessage{

    private static final long serialVersionUID = 3899442233642127636L;

    public PongMessage(Short senderPort, Short receiverPort, String message) {
        super(MessageType.PONG, senderPort, receiverPort, message);
    }
}
