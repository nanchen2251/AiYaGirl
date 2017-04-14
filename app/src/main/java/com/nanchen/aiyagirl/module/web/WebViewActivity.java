package com.nanchen.aiyagirl.module.web;

import android.os.Bundle;

import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.base.BaseActivity;

public class WebViewActivity extends BaseActivity {

    public static final String GANK_URL = "com.nanchen.aiyagirl.module.web.WebViewActivity.gank_url";
    public static final String GANK_TITLE = "com.nanchen.aiyagirl.module.web.WebViewActivity.gank_title";


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
