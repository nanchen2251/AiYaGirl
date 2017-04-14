package com.nanchen.aiyagirl.module.web;

import android.app.Activity;
import android.content.Intent;

import com.nanchen.aiyagirl.module.web.WebContract.IWebPresenter;
import com.nanchen.aiyagirl.module.web.WebContract.IWebView;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  14:41
 */

public class WebPresenter implements IWebPresenter {
    private IWebView mWebView;
    private String mGankUrl;
    private Activity mActivity;

    public WebPresenter(IWebView webView){
        this.mWebView = webView;
    }


    @Override
    public void subscribe() {
        mActivity = mWebView.getWebViewContext();
        Intent intent = mActivity.getIntent();
        mWebView.setGankTitle(intent.getStringExtra(WebViewActivity.GANK_TITLE));
        mWebView.initWebView();
        mGankUrl = intent.getStringExtra(WebViewActivity.GANK_URL);
        mWebView.loadGankUrl(mGankUrl);
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public String getGankUrl() {
        return this.mGankUrl;
    }
}
