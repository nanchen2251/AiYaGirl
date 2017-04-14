package com.nanchen.aiyagirl;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  10:41
 */

public enum ConfigManage {
    INSTANCE;

    private final String spName = "app_config";
    private final String key_isListShowImg = "isListShowImg";
    private final String key_thumbnailQuality = "thumbnailQuality";

    private boolean isListShowImg;
    private int thumbnailQuality;

    public void initConfig(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        // 列表是否显示图片
        isListShowImg = sharedPreferences.getBoolean(key_isListShowImg, true);
        // 缩略图质量 0：原图 1：默认 2：省流
        thumbnailQuality = sharedPreferences.getInt(key_thumbnailQuality, 1);
    }

    public boolean isListShowImg() {
        return isListShowImg;
    }

    public void setListShowImg(boolean listShowImg) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key_isListShowImg, listShowImg);
        if (editor.commit()) {
            isListShowImg = listShowImg;
        }
    }

    public int getThumbnailQuality() {
        return thumbnailQuality;
    }

    public void setThumbnailQuality(int thumbnailQuality) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key_thumbnailQuality, thumbnailQuality);
        if (editor.commit()) {
            this.thumbnailQuality = thumbnailQuality;
        }
    }
}
