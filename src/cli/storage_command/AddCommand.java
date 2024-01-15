package cli.storage_command;

import app.AppConfig;
import app.AppTool;
import app.file.FileInfo;
import app.file.FileTool;
import cli.command.CLICommand;
import mutex.MutexToken;

import java.util.List;

public class AddCommand implements CLICommand {


    @Override
    public String commandName() {
        return "add";
    }

    @Override
    public void execute(String args) {
        if (args == null || args.isEmpty()) {
            AppTool.timestampedStandardPrint("You need to provide file path.");
            return;
        }

        String path = args.replace('/' , '\\');

        MutexToken.lock();
        AppTool.timestampedErrorPrint("usao u lock");

        if (FileTool.isPathFile(AppConfig.ROOT_DIR, path)) {
            FileInfo fileInfo = FileTool.getFileInfoFromPath(AppConfig.ROOT_DIR, path);
            if (fileInfo != null) {
                AppConfig.storage.addToStorage(fileInfo, AppConfig.myServentInfo.getListenerPort());
            }
        } else {
            List<FileInfo> fileInfoList = FileTool.getDirectoryInfoFromPath(AppConfig.ROOT_DIR, path);
            if (!fileInfoList.isEmpty()) {
                for (FileInfo fileInfo : fileInfoList) {
                    AppConfig.storage.addToStorage(fileInfo, AppConfig.myServentInfo.getListenerPort());
                }
            }
        }

        MutexToken.unlock();
        AppTool.timestampedErrorPrint("izasao iz locka");

    }
}
