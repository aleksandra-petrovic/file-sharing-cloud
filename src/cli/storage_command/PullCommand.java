package cli.storage_command;

import app.AppConfig;
import app.AppTool;
import cli.command.CLICommand;

public class PullCommand implements CLICommand {
    @Override
    public String commandName() {
        return "pull";
    }

    @Override
    public void execute(String args) {
        if (args == null || args.isEmpty()) {
            AppTool.timestampedStandardPrint("You need to provide file path.");
            return;
        }

        String path = args.replace('/' , '\\');

        AppConfig.storage.pullFile(path);
    }
}
