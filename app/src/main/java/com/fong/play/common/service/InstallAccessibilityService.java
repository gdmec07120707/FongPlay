package com.fong.play.common.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.service
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class InstallAccessibilityService extends AccessibilityService {



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onAccessibilityEvent(AccessibilityEvent event) {


       AccessibilityNodeInfo nodeInfo =  event.getSource();

        if(nodeInfo==null){
            return;
        }


        int evenType = event.getEventType();


        if(evenType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
                evenType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)
        {



            // 中文系统
            click("安装");
            click("下一步");
            click("确定");
            click("完成");

            // 英文


        }






    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void click(String text){



        AccessibilityNodeInfo rootNodeInfo =  getRootInActiveWindow();


       List<AccessibilityNodeInfo> nodeInfos =  rootNodeInfo.findAccessibilityNodeInfosByText(text);

        if(nodeInfos==null ){
            return;
        }


        for (AccessibilityNodeInfo info : nodeInfos){

            if(info.getClassName().equals("android.widget.Button") && info.isClickable()){

                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }

        }



    }




    @Override
    public void onInterrupt() {

    }
}
