package com.nanchen.aiyagirl

import android.content.Context

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  10:41
 */

enum class ConfigManage {
    INSTANCE;

    private val spName = "app_config"
    private val key_isListShowImg = "isListShowImg"
    private val key_thumbnailQuality = "thumbnailQuality"

    private var isListShowImg: Boolean = false
    private var thumbnailQuality: Int = 0

    fun initConfig(context: Context) {
        val sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        // 列表是否显示图片
        isListShowImg = sharedPreferences.getBoolean(key_isListShowImg, true)
        // 缩略图质量 0：原图 1：默认 2：省流
        thumbnailQuality = sharedPreferences.getInt(key_thumbnailQuality, 1)
    }

    fun isListShowImg(): Boolean {
        return isListShowImg
    }

    fun setListShowImg(listShowImg: Boolean) {
        val sharedPreferences = App.instance.getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key_isListShowImg, listShowImg)
        if (editor.commit()) {
            isListShowImg = listShowImg
        }
    }

    fun getThumbnailQuality(): Int {
        return thumbnailQuality
    }

    fun setThumbnailQuality(thumbnailQuality: Int) {
        val sharedPreferences = App.instance.getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key_thumbnailQuality, thumbnailQuality)
        if (editor.commit()) {
            this.thumbnailQuality = thumbnailQuality
        }
    }
}
