package app.file;

import app.AppConfig;
import app.AppTool;
import servent.message.Message;
import servent.message.PullAskMessage;
import servent.message.util.MessageUtil;

import java.io.File;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    public static Map<String, FileInfo> myFiles = new HashMap<>();

    public void addToStorage(FileInfo fileInfo, short nodePort) {
        if (!myFiles.containsKey(fileInfo.getPath())) {
            myFiles.put(fileInfo.getPath(), fileInfo);
            AppTool.timestampedStandardPrint("File " + fileInfo.getPath() + " stored successfully.");

            AppTool.timestampedStandardPrint("After adding file to storage.");
            for (Map.Entry<String, FileInfo> map: myFiles.entrySet()) {
                AppTool.timestampedStandardPrint("storage : " + map.getKey() + " ** " + map.getValue() + " ** " + map.getValue().getNodeId());
            }
        }
        else {
            AppTool.timestampedStandardPrint("File already exists in storage. " + fileInfo.getPath());
        }
    }

    public Map<String, FileInfo> getMyFiles() {
        return myFiles;
    }

    public void addAllToStorage(Map<String,FileInfo> fileInfoMap, short nodePort){
        for (Map.Entry<String, FileInfo> entry : fileInfoMap.entrySet()) {
            FileInfo value = entry.getValue();
            addToStorage(value, nodePort);
        }
    }

    public void pullFile(String filePath){
        if(myFiles.containsKey(filePath)){
            AppTool.timestampedStandardPrint("Pulled file " + filePath);
            AppTool.timestampedErrorPrint(myFiles.get(filePath).getContent());
        }else{
            MessageUtil.sendMessage(new PullAskMessage(AppConfig.myServentInfo.getListenerPort(),
                                            AppConfig.pingingInfo.getSecondSuccessor(), filePath));
        }
    }
}
