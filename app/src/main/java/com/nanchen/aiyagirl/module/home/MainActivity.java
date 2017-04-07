package com.nanchen.aiyagirl.module.home;

import android.os.Bundle;
import android.widget.Toast;

import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.base.BaseActivity;


/**
 * 主页面
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  15:31
 */

public class MainActivity extends BaseActivity {

    // 保存用户按返回键的时间
    private long mExitTime = 0;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, R.string.exit_toast, Toast.LENGTH_SHORT)
                    .show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
