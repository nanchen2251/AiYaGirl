package com.nanchen.aiyagirl.utils

import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * 包相关工具类
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-20  17:22
 */

object PackageUtil {

    /**
     * 获取当前应用的版本号
     */
    // 获取packagemanager的实例
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    val versionName: String
        get() {
            val packageManager = Utils.getContext().packageManager
            val packInfo: PackageInfo?
            return try {
                packInfo = packageManager.getPackageInfo(Utils.getContext().packageName, 0)
                packInfo!!.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                "1.0"
            }

        }
}
