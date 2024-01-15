package servent.message;

import app.ServentInfo;

public class PingMessage extends BasicMessage{
    private static final long serialVersionUID = 3899445286642127636L;

    private String portForCheckup;

    public PingMessage(Short senderPort, Short receiverPort, String message, String portForCheckup) {
        super(MessageType.PING, senderPort, receiverPort, message);
        this.portForCheckup = portForCheckup;
    }

    public String getPortForCheckup() {
        return portForCheckup;
    }
}
