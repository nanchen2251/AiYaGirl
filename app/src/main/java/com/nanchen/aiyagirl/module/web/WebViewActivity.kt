package com.nanchen.aiyagirl.module.web

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import com.nanchen.aiyagirl.R
import com.nanchen.aiyagirl.base.BaseActivity
import com.nanchen.aiyagirl.module.web.WebContract.IWebPresenter
import com.nanchen.aiyagirl.module.web.WebContract.IWebView
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : BaseActivity(), IWebView {
    lateinit var mWebTitle: TextView
    lateinit var mWebToolbar: Toolbar
    lateinit var mWebProgressBar: ProgressBar
    lateinit var mWebAppbar: AppBarLayout
    lateinit var mWebView: WebView

    private lateinit var mWebPresenter: IWebPresenter

    override val contentViewLayoutID: Int
        get() = R.layout.activity_web_view

    override val webViewContext: Activity
        get() = this

    companion object {
        const val GANK_URL = "com.nanchen.aiyagirl.module.web.WebViewActivity.gank_url"
        const val GANK_TITLE = "com.nanchen.aiyagirl.module.web.WebViewActivity.gank_title"

        fun start(context: Context, url: String, title: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.GANK_TITLE, title)
            intent.putExtra(WebViewActivity.GANK_URL, url)
            context.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mWebTitle = web_title
        mWebToolbar = web_toolbar
        mWebProgressBar = web_progressBar
        mWebAppbar = web_appbar
        mWebView = web_view

        mWebPresenter = WebPresenter(this)

        mWebToolbar.setNavigationOnClickListener { finish() }

        mWebPresenter.subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        mWebPresenter.unSubscribe()
    }

    override fun setGankTitle(title: String) {
        mWebTitle.text = title
    }

    override fun loadGankUrl(url: String) {
        mWebView.loadUrl(url)
    }

    override fun initWebView() {
        val settings = mWebView.settings
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)

        mWebView.webChromeClient = MyWebChrome()
        mWebView.webViewClient = MyWebClient()
    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack()
        } else {
            finish()
        }
    }

    private inner class MyWebChrome : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            mWebProgressBar.visibility = View.VISIBLE
            mWebProgressBar.progress = newProgress
        }
    }

    private inner class MyWebClient : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            mWebProgressBar.visibility = View.GONE
        }
    }

}
