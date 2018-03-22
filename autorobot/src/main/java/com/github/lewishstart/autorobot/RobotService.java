package com.github.lewishstart.autorobot;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

/**
 * author: sundong
 * created at 2018/3/22 17:02
 */

public class RobotService extends BaseAccessibilityService {
    AccessibilityNodeInfo itemNodeinfo;
    boolean hasAction = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        android.util.Log.d("maptrix", "get event = " + eventType);
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗口改变事件
                android.util.Log.d("maptrix", "get type window down event");
                itemNodeinfo = null;
                String className = event.getClassName().toString();
                if (className.equals("com.tencent.mm.ui.LauncherUI")) {
                    Toast.makeText(RobotService.this, "打开微信了", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void onInterrupt() {

    }
}
