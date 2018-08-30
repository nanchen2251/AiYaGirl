package com.nanchen.aiyagirl.utils

import android.content.res.Resources
import android.graphics.drawable.Drawable

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-25  14:03
 */

object ResourceHelper {

    val resource: Resources
        get() = Utils.getContext().resources

    fun getString(resId: Int): String {
        return resource.getString(resId)
    }

    fun getDrawable(resId: Int): Drawable {
        return resource.getDrawable(resId)
    }

    fun getColor(resId: Int): Int {
        return resource.getColor(resId)
    }

    fun getDimens(resId: Int): Float {
        return resource.getDimension(resId)
    }
}
