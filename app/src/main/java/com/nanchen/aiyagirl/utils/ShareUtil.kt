package com.nanchen.aiyagirl.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

import com.nanchen.aiyagirl.R

/**
 * 专用于分享的工具类
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-20  11:56
 */

object ShareUtil {
    fun share(context: Context, stringRes: Int) {
        share(context, context.getString(stringRes))
    }

    fun shareImage(context: Context, uri: Uri, title: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.type = "image/jpeg"
        context.startActivity(Intent.createChooser(shareIntent, title))
    }


    fun share(context: Context, extraText: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.action_share))
        intent.putExtra(Intent.EXTRA_TEXT, extraText)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.action_share)))
    }
}
