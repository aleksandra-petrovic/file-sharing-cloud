package cli;

import app.AppConfig;
import app.AppTool;
import app.Cancellable;
import cli.command.CLICommand;
import cli.command.InfoCommand;
import cli.command.PauseCommand;
import cli.command.StopCommand;
import cli.storage_command.AddCommand;
import cli.storage_command.PullCommand;
import cli.storage_command.RemoveCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIParser implements Runnable, Cancellable {

    private volatile boolean working = true;
    private final List<CLICommand> commandList;

    public CLIParser(){
        this.commandList = new ArrayList<>();

        commandList.add(new PauseCommand());
        commandList.add(new StopCommand(this));
        commandList.add(new InfoCommand());
        commandList.add(new AddCommand());
        commandList.add(new PullCommand());
        commandList.add(new RemoveCommand());
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (working) {
            String commandLine = sc.nextLine();

            int spacePos = commandLine.indexOf(" ");

            String commandName = null;
            String commandArgs = null;
            if (spacePos != -1) {
                commandName = commandLine.substring(0, spacePos);
                commandArgs = commandLine.substring(spacePos+1, commandLine.length());
            } else {
                commandName = commandLine;
            }

            boolean found = false;

            for (CLICommand cliCommand : commandList) {
                if (cliCommand.commandName().equals(commandName)) {
                    cliCommand.execute(commandArgs);
                    found = true;
                    break;
                }
            }

            if (!found) {
                AppTool.timestampedErrorPrint("Unknown command: " + commandName);
            }
        }

        sc.close();
    }

    @Override
    public void stop() {
        this.working = false;
    }
}
