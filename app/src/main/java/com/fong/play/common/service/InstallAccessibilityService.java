package com.fong.play.common.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * @version V1.0
 * @Description: ${TODO}(自动安装accessibility)
 * @date
 */

public class InstallAccessibilityService extends AccessibilityService {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo nodeInfo = accessibilityEvent.getSource();
        if(nodeInfo==null){
            return;
        }

        int eventType = accessibilityEvent.getEventType();
        //监听窗口变化
        if(eventType==AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED||
                eventType== AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED){
            // 中文系统
            click("安装");
            click("下一步");
            click("确定");
            click("完成");
        }

    }

    @Override
    public void onInterrupt() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void click(String text){
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        if(rootNodeInfo==null){
            return;
        }
        //如何获取UI元素
        List<AccessibilityNodeInfo> nodeInfos = rootNodeInfo.findAccessibilityNodeInfosByText(text);
        if(nodeInfos==null){
            return;
        }

        for(AccessibilityNodeInfo info :nodeInfos){
            //如果点击按钮并且可以点击
            if(info.getClassName().equals("android.widget.Button")&&info.isClickable()){
                //模拟事件
                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }
}
