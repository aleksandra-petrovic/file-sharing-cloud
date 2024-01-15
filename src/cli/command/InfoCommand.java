package cli.command;

import app.AppConfig;
import app.AppTool;

public class InfoCommand implements CLICommand{

    @Override
    public String commandName() {
        return "info";
    }

    @Override
    public void execute(String args) {
        AppTool.timestampedStandardPrint("all servents " + AppConfig.allServents.size() + " " + AppConfig.allServents);
        AppTool.timestampedStandardPrint("My neighbours are: " + AppConfig.myServentInfo.getNeighbors());
    }
}
