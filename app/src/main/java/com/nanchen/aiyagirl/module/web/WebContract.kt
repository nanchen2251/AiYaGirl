package com.nanchen.aiyagirl.module.web

import android.app.Activity

import com.nanchen.aiyagirl.base.BasePresenter
import com.nanchen.aiyagirl.base.BaseView

/**
 * WebContract
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  14:38
 */

interface WebContract {

    interface IWebView : BaseView {
        val webViewContext: Activity

        fun setGankTitle(title: String)

        fun loadGankUrl(url: String)

        fun initWebView()
    }

    interface IWebPresenter : BasePresenter {
        val gankUrl: String
    }
}
