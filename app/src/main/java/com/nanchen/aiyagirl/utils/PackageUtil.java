package com.nanchen.aiyagirl.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 包相关工具类
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-20  17:22
 */

public class PackageUtil {

    /**
     * 获取当前应用的版本号
     */
    public static String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = Utils.getContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(Utils.getContext().getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }
}
