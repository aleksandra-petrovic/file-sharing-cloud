package servent.message;

import app.ServentInfo;

public class TokenMessage extends BasicMessage{
    private static final long serialVersionUID = 38977886642127636L;

    public TokenMessage(Short senderPort, Short receiverPort) {
        super(MessageType.TOKEN, senderPort, receiverPort, "Hi. Im giving the token to you.");
    }

}
