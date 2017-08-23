package com.nanchen.aiyagirl;

import android.app.Activity;
import android.app.Application;

import com.nanchen.aiyagirl.utils.Utils;
import com.squareup.leakcanary.LeakCanary;

import java.util.HashSet;
import java.util.Set;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * 应用程序
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  14:41
 */

public class App extends Application {
    private static App INSTANCE;
    private Set<Activity> mActivities;

    public static synchronized App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        // 初始化 LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        BGASwipeBackManager.getInstance().init(this);
        ConfigManage.INSTANCE.initConfig(this);
        Utils.init(this);
    }

    public void addActivity(Activity activity) {
        if (mActivities == null) {
            mActivities = new HashSet<>();
        }
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (mActivities != null) {
            mActivities.remove(activity);
        }
    }

    public void exitApp() {
        if (mActivities != null) {
            synchronized (mActivities) {
                for (Activity activity :
                        mActivities) {
                    activity.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
