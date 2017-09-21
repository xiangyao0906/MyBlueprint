package com.ming.blueprint.mvp.base;

import android.app.Activity;

import com.ming.blueprint.mvp.utils.LogUtils;

import java.util.LinkedList;

/**
 * Created by ming on 2016/12/14 11:21.
 * Explain:activity栈
 * 缺陷：当Activity不正常退出时，无法移除。会导致内存问题 o(╯□╰)o
 */
public class ActivityStackManager {

    public LinkedList<Activity> activityList = new LinkedList<>();
    private static volatile ActivityStackManager instance;

    public static ActivityStackManager getInstance() {
        if (instance == null) {
            synchronized (ActivityStackManager.class) {
                if (instance == null) {
                    instance = new ActivityStackManager();
                }
            }
        }
        return instance;
    }

    private ActivityStackManager() {
    }

    /**
     * 将Activity添加到activityList中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 获取栈顶Activity
     *
     * @return
     */
    public Activity getLastActivity() {
        return activityList.getLast();
    }

    /**
     * 将Activity移除
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (null != activity && activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    /**
     * 判断某一Activity是否在运行
     *
     * @param className
     * @return
     */
    public boolean isActivityRunning(String className) {
        if (className != null) {
            for (Activity activity : activityList) {
                if (activity.getClass().getName().equals(className)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 退出所有的Activity
     */
    public void finishAllActivity() {
//        for (Activity activity : activityList) {
//            if (null != activity) {
//                activity.finish();
//                LogUtils.e("finish name：" + activity.getClass().getName());
//            }
//        }
        for (int i = activityList.size() - 1; i >= 0; i--) {
            if (null != activityList.get(i)) {
                activityList.get(i).finish();
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

}
