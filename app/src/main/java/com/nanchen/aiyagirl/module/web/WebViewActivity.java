package com.nanchen.aiyagirl.module.web;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.base.BaseActivity;
import com.nanchen.aiyagirl.module.web.WebContract.IWebPresenter;
import com.nanchen.aiyagirl.module.web.WebContract.IWebView;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity implements IWebView{

    public static final String GANK_URL = "com.nanchen.aiyagirl.module.web.WebViewActivity.gank_url";
    public static final String GANK_TITLE = "com.nanchen.aiyagirl.module.web.WebViewActivity.gank_title";
    @BindView(R.id.web_title)
    TextView mWebTitle;
    @BindView(R.id.web_toolbar)
    Toolbar mWebToolbar;
    @BindView(R.id.web_progressBar)
    ProgressBar mWebProgressBar;
    @BindView(R.id.web_appbar)
    AppBarLayout mWebAppbar;
    @BindView(R.id.web_view)
    WebView mWebView;

    private IWebPresenter mWebPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_web_view;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {



        mWebPresenter = new WebPresenter(this);

        mWebToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebPresenter.unSubscribe();
    }

    @Override
    public Activity getWebViewContext() {
        return this;
    }

    @Override
    public void setGankTitle(String title) {
        mWebTitle.setText(title);
    }

    @Override
    public void loadGankUrl(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        mWebView.setWebChromeClient(new MyWebChrome());
        mWebView.setWebViewClient(new MyWebClient());
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    private class MyWebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mWebProgressBar.setVisibility(View.VISIBLE);
            mWebProgressBar.setProgress(newProgress);
        }
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            mWebProgressBar.setVisibility(View.GONE);
        }
    }
}
