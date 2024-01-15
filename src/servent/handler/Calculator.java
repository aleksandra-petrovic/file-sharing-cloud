package servent.handler;

import app.AppConfig;
import app.AppTool;
import app.ServentInfo;

import java.util.*;

public class Calculator {

    public static void calculateAndSet(){
        List<Short> allPorts = new ArrayList<>();
        if(AppConfig.allServents != null) {
            Set<ServentInfo> allServents = new HashSet<>(AppConfig.allServents);
            for (ServentInfo serventInfo : allServents) {
                allPorts.add(serventInfo.getListenerPort());
            }
        }

        Collections.sort(allPorts);

        int myIdx = allPorts.indexOf(AppConfig.myServentInfo.getListenerPort());
        int pred1, pred2, succ1, succ2;

        succ1 = myIdx + 1 % allPorts.size();
        pred1 = myIdx - 1 == -1 ? allPorts.size() - 1 : myIdx - 1;
        succ2 = myIdx + 2 % allPorts.size();
        pred2 = myIdx - 2 < 0 ? allPorts.size() + myIdx - 2 : myIdx - 2;


        AppConfig.myServentInfo.clearNeighbors();

        AppConfig.pingingInfo.setNodeToPing(allPorts.get(succ1));
        AppConfig.myServentInfo.addNeighbor(allPorts.get(succ1));

        AppConfig.pingingInfo.setBackupNode(allPorts.get(pred1));
        AppConfig.myServentInfo.addNeighbor(allPorts.get(pred1));

        AppConfig.pingingInfo.setSecondSuccessor(allPorts.get(succ2));
        AppConfig.myServentInfo.addNeighbor(allPorts.get(succ2));

        AppConfig.pingingInfo.setSecondPredecessor(allPorts.get(pred2));
        AppConfig.myServentInfo.addNeighbor(allPorts.get(pred2));


        AppTool.timestampedStandardPrint("My neighbours are: " + AppConfig.pingingInfo.getNodeToPing() + " " +
                AppConfig.pingingInfo.getBackupNode() + " " + AppConfig.pingingInfo.getSecondSuccessor() + " " +
                AppConfig.pingingInfo.getSecondPredecessor());

    }
}
