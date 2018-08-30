package com.nanchen.aiyagirl.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2018-01-19  17:26
 */

class AndroidWorkaround private constructor(private val mChildOfContent: View) {
    private var usableHeightPrevious: Int = 0
    private val frameLayoutParams: ViewGroup.LayoutParams

    init {
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
        frameLayoutParams = mChildOfContent.layoutParams
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {

            frameLayoutParams.height = usableHeightNow
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom
    }

    companion object {
        fun assistActivity(content: View) {
            AndroidWorkaround(content)
        }

        fun checkDeviceHasNavigationBar(context: Context): Boolean {
            var hasNavigationBar = false
            val rs = context.resources
            val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            try {
                @SuppressLint("PrivateApi") val systemPropertiesClass = Class.forName("android.os.SystemProperties")
                val m = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1" == navBarOverride) {
                    hasNavigationBar = false
                } else if ("0" == navBarOverride) {
                    hasNavigationBar = true
                }
            } catch (e: Exception) {

            }

            return hasNavigationBar

        }
    }
}