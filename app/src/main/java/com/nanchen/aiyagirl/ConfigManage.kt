package com.nanchen.aiyagirl

import android.content.Context

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  10:41
 */

object ConfigManage {

    private const val spName = "app_config"
    private const val key_isListShowImg = "isListShowImg"
    private const val key_thumbnailQuality = "thumbnailQuality"

    private var isListShowImg: Boolean = false
    private var thumbnailQuality: Int = 0

    @JvmStatic fun initConfig(context: Context) {
        val sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        // 列表是否显示图片
        isListShowImg = sharedPreferences.getBoolean(key_isListShowImg, true)
        // 缩略图质量 0：原图 1：默认 2：省流
        thumbnailQuality = sharedPreferences.getInt(key_thumbnailQuality, 1)
    }

    @JvmStatic fun isListShowImg(): Boolean {
        return isListShowImg
    }

    @JvmStatic fun setListShowImg(listShowImg: Boolean) {
        val sharedPreferences = App.instance.getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key_isListShowImg, listShowImg)
        if (editor.commit()) {
            isListShowImg = listShowImg
        }
    }

    @JvmStatic fun getThumbnailQuality(): Int {
        return thumbnailQuality
    }

    @JvmStatic fun setThumbnailQuality(thumbnailQuality: Int) {
        val sharedPreferences = App.instance.getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key_thumbnailQuality, thumbnailQuality)
        if (editor.commit()) {
            this.thumbnailQuality = thumbnailQuality
        }
    }
}
