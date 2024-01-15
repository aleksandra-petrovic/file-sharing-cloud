package mutex;

import app.AppConfig;
import app.AppTool;
import app.ServentInfo;
import servent.message.TokenMessage;
import servent.message.WantTokenMessage;
import servent.message.util.MessageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MutexToken {

    private static volatile boolean haveToken = false;
    private static volatile boolean wantToken = false;

    public static void init() {
        haveToken = true;
    }

    public static void lock(){
        wantToken = true;

        for(ServentInfo serventInfo: AppConfig.allServents){
            MessageUtil.sendMessage(new WantTokenMessage(AppConfig.myServentInfo.getListenerPort(), serventInfo.getListenerPort()));
        }

        long sleepTime = 1;
        while(!haveToken){
            try{
                Thread.sleep(sleepTime);
                sleepTime = (sleepTime * 2) > 100 ? 100 : (sleepTime * 2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void unlock(){
        haveToken = false;
        wantToken = false;
        sendToken();
    }

    public static void receiveToken(){
        if(wantToken){
            haveToken = true;
        } else {
            sendToken();
        }
    }

    private static void sendToken() {
        //send token message to next in line
        if(AppConfig.tokenQueue.poll() == null) {
            MessageUtil.sendMessage(new TokenMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.myServentInfo.getListenerPort()));
        }else{
            MessageUtil.sendMessage(new TokenMessage(AppConfig.myServentInfo.getListenerPort(), AppConfig.tokenQueue.poll()));
        }
    }
}
