package com.nanchen.aiyagirl.utils;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.Calendar;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-20  10:54
 */

public abstract class PerfectClickListener implements OnClickListener {
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private int id = -1;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        int mId = v.getId();
        if (id != mId) {
            id = mId;
            lastClickTime = currentTime;
            onNoDoubleClick(v);
            return;
        }
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);
}
