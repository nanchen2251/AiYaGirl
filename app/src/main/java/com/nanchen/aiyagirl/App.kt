package com.nanchen.aiyagirl

import android.app.Application
import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager
import com.nanchen.aiyagirl.utils.Utils
import com.squareup.leakcanary.LeakCanary

/**
 * 应用程序
 *
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  14:41
 */

class App : Application() {

    init {
        instance = this
    }

    companion object {
        @get:Synchronized
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        // 初始化 LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        BGASwipeBackManager.getInstance().init(this)
        ConfigManage.INSTANCE.initConfig(this)
        Utils.init(this)
    }

    fun exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

}
