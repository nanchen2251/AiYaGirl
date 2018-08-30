package com.nanchen.aiyagirl.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

import java.lang.reflect.Field


/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-19  9:37
 */

object ScreenUtil {
    // 获取屏幕的宽度
    val screenWidth: Int
        get() {
            val dm = DisplayMetrics()
            val wm = Utils.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            wm.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }

    // 获取屏幕的宽度
    fun getScreenWidth(activity: Activity): Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    // 获取屏幕的高度
    fun getScreenHeight(activity: Activity): Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    @SuppressLint("PrivateApi")
    //获取手机状态栏高度
    fun getStatusBarHeight(context: Context): Int {
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c!!.newInstance()
            field = c.getField("status_bar_height")
            x = Integer.parseInt(field!!.get(obj).toString())
            statusBarHeight = context.resources.getDimensionPixelSize(x)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

        return statusBarHeight
    }

    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}
