package com.nanchen.aiyagirl.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-19  9:38
 */

class Utils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        /**
         * 初始化工具类
         *
         * @param context 上下文
         */
        fun init(context: Context) {
            Utils.context = context.applicationContext
        }

        /**
         * 获取ApplicationContext
         *
         * @return ApplicationContext
         */
        fun getContext(): Context {
            if (context != null) return context!!
            throw NullPointerException("u should init first")
        }

        /**
         * 使用浏览器打开链接
         */
        fun openLink(context: Context, content: String) {
            val issuesUrl = Uri.parse(content)
            val intent = Intent(Intent.ACTION_VIEW, issuesUrl)
            context.startActivity(intent)
        }
    }
}
