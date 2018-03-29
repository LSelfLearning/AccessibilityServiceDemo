package com.github.lewishstart.autorobot;

import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.io.DataOutputStream;
import java.io.OutputStream;


/**
 * author: sundong
 * created at 2018/3/22 17:02
 */

public class RobotService extends BaseAccessibilityService {
    private static final String TAG = "RobotService";
    private String action = "";
    private static final String LAUNCHER_UI_CLASS_NAME = "com.tencent.mm.ui.LauncherUI";
    private static final String SCAN_UI_CLASS_NAME = "com.tencent.mm.plugin.scanner.ui.BaseScanUI";

    private static final String AlbumPreviewUI = "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI";
    private static final String jiaqunUI = "com.tencent.mm.plugin.webview.ui.tools.WebViewUI";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null) {
            action = intent.getStringExtra("action");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "-----------------------onAccessibilityEvent---------------------");
        int eventType = event.getEventType();
        CharSequence className = event.getClassName();
        Log.d(TAG, "packageName:" + event.getPackageName() + "");//响应事件的包名，也就是哪个应用才响应了这个事件
        Log.d(TAG, "source:" + event.getSource() + "");//事件源信息
        Log.d(TAG, "event class name:" + event.getClassName() + "");//事件源的类名，比如android.widget.TextView
        Log.d(TAG, "event type(int):" + eventType + "");
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                Log.d(TAG, "eventType:TYPE_NOTIFICATION_STATE_CHANGED ");
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗口改变事件
                Log.d(TAG, "eventType:TYPE_WINDOW_STATE_CHANGED");
                if (action.equals("jiaqun")) {
                    if (LAUNCHER_UI_CLASS_NAME.equals(className)) {
                        SystemClock.sleep(2000);
                        performViewClick(findViewByID("com.tencent.mm:id/g1"));
                        SystemClock.sleep(2000);
                        performViewClick(findViewByText("扫一扫"));
                    } else if (SCAN_UI_CLASS_NAME.equals(className)) {
                        SystemClock.sleep(2000);
                        performViewClick(findViewByID("com.tencent.mm:id/h6"));
                        SystemClock.sleep(2000);
                        performViewClick(findViewByText("从相册选取二维码"));
                    } else if (AlbumPreviewUI.equals(className)) {
                        SystemClock.sleep(2000);
                        performViewClick(findViewByID("com.tencent.mm:id/f7"));
                    } else if (jiaqunUI.equals(className)) {
                        SystemClock.sleep(2000);
                        execShellCmd("input tap 533 1020");
                    }
                }

                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED://View获取到焦点
                Log.d(TAG, "event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                Log.d(TAG, "event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                Log.d(TAG, "event type:TYPE_GESTURE_DETECTION_END");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                Log.d(TAG, "event type:TYPE_WINDOW_CONTENT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.d(TAG, "event type:TYPE_VIEW_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                Log.d(TAG, "event type:TYPE_VIEW_TEXT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                Log.d(TAG, "event type:TYPE_VIEW_SCROLLED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                Log.d(TAG, "event type:TYPE_VIEW_TEXT_SELECTION_CHANGED");
                break;

        }
        for (
                CharSequence txt : event.getText())

        {
            Log.d(TAG, "text:" + txt);
        }
        Log.d(TAG, "-------------------------------------------------------------");
    }

    @Override
    public void onInterrupt() {

    }

    private void execShellCmd(String cmd) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
