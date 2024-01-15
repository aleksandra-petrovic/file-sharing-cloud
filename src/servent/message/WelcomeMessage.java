package servent.message;

import app.AppConfig;
import app.ServentInfo;

import java.util.List;
import java.util.Set;

public class WelcomeMessage extends BasicMessage{
    private static final long serialVersionUID = -6211293544524749872L;

    private List<Short> neighbors;
    private Set<ServentInfo> allServents;

    public WelcomeMessage(short senderPort, short receiverPort) {
        super(MessageType.WELCOME, senderPort, receiverPort, "Welcome to the system new node!");
        this.allServents = AppConfig.allServents;
    }
    public Set<ServentInfo> getAllServents() {
        return allServents;
    }

}
