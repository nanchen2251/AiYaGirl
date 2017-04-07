package com.nanchen.aiyagirl;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * 应用程序
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  14:41
 */

public class App extends Application {
    private static App INSTANCE;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        BGASwipeBackManager.getInstance().init(this);
        INSTANCE = this;
    }
}
