package servent.message;

public class WantTokenMessage extends BasicMessage{
    private static final long serialVersionUID = 3728986642127636L;

    public WantTokenMessage(Short senderPort, Short receiverPort) {
        super(MessageType.WANT_TOKEN, senderPort, receiverPort, "Hi. I want the token.");
    }

}
