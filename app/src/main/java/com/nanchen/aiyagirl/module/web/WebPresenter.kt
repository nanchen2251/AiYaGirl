package com.nanchen.aiyagirl.module.web

import android.app.Activity
import com.nanchen.aiyagirl.module.web.WebContract.IWebPresenter
import com.nanchen.aiyagirl.module.web.WebContract.IWebView

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  14:41
 */

class WebPresenter(private val mWebView: IWebView) : IWebPresenter {
    override lateinit var gankUrl: String
    private var mActivity: Activity? = null


    override fun subscribe() {
        mActivity = mWebView.webViewContext
        val intent = mActivity!!.intent
        mWebView.setGankTitle(intent.getStringExtra(WebViewActivity.GANK_TITLE))
        mWebView.initWebView()
        gankUrl = intent.getStringExtra(WebViewActivity.GANK_URL)
        mWebView.loadGankUrl(gankUrl)
    }


    override fun unSubscribe() {

    }
}
