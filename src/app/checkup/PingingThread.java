package app.checkup;

import app.AppConfig;
import app.AppTool;
import app.Cancellable;
import app.ServentInfo;
import servent.message.BackupMessage;
import servent.message.Message;
import servent.message.PingMessage;
import servent.message.util.MessageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PingingThread implements Runnable, Cancellable {

    private volatile boolean working = true;

    public PingingThread() {}

    @Override
    public void run() {
        while(working){
            try {
                Thread.sleep(1000);

                if(AppConfig.pingingInfo.getNodeToPing() != -1 && AppConfig.pingingInfo.getBackupNode() != -1){
                    AppConfig.pingingInfo.setDoneChecking(false);
                    AppConfig.pingingInfo.setTotalPings(AppConfig.pingingInfo.getTotalPings() + 1);

                    MessageUtil.sendMessage(new PingMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.pingingInfo.getNodeToPing(), "Ping", "0"));

                    if(AppConfig.pingingInfo.getTotalPings() % 10 == 0){
                        AppTool.timestampedStandardPrint("Sending backup... at " + String.valueOf(AppConfig.pingingInfo.getTotalPings()) + " total pings");
                        //slanje backup-a
                        //new backupmsg(myinfo, backupnode)
                        MessageUtil.sendMessage(new BackupMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.pingingInfo.getBackupNode(), AppConfig.storage.getMyFiles()));
                    }

                    int timeWaiting = 0;
                    boolean gotResponse = false;

                    try {
                        //skip the delay
                        Thread.sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    while(true){
                        //waiting for response
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        timeWaiting += 1000;

                        if(AppConfig.pingingInfo.getTotalPings() == AppConfig.pingingInfo.getTotalPongs()){
                            gotResponse = true;
                            break;
                        }
                        if(timeWaiting == AppConfig.WEAK_FAILURE_LIMIT){
                            break;
                        }
                    }

                    if (!gotResponse){
                        AppTool.timestampedStandardPrint("Panic! My neighbor is not ok.");
                        //send isok message
                        List<Short> allPorts = new ArrayList<>();
                        for(ServentInfo serventInfo : AppConfig.allServents){
                            allPorts.add(serventInfo.getListenerPort());
                        }
                        Collections.sort(allPorts);
                        MessageUtil.sendMessage(new PingMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.pingingInfo.getSecondSuccessor(), "IsOk?",
                                String.valueOf(AppConfig.pingingInfo.getNodeToPing())));
                        while(!AppConfig.pingingInfo.isDoneChecking()){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }

                    }else{
                        AppTool.timestampedStandardPrint("All good.");
                    }

                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void stop() {
        AppTool.timestampedStandardPrint("Stopping the Pinging Thread...");
        this.working = false;
    }
}
