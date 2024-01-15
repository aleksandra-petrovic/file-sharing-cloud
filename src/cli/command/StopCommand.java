package cli.command;

import app.AppConfig;
import app.AppTool;
import cli.CLIParser;
import servent.message.DeletedNodeUpdateMessage;
import servent.message.util.MessageUtil;

public class StopCommand implements CLICommand{

    private CLIParser parser;

    public StopCommand(CLIParser parser) {
        this.parser = parser;
    }

    @Override
    public String commandName() {
        return "stop";
    }

    @Override
    public void execute(String args) {
        AppTool.timestampedStandardPrint("Stopping...");

        for(Short neighborPort: AppConfig.myServentInfo.getNeighbors()){
            AppTool.timestampedErrorPrint(String.valueOf(neighborPort));
            MessageUtil.sendMessage(new DeletedNodeUpdateMessage(AppConfig.myServentInfo.getListenerPort(), neighborPort, AppConfig.myServentInfo));
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        parser.stop();

        System.exit(0);
    }

}