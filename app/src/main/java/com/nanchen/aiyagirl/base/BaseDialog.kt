package com.nanchen.aiyagirl.base

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.nanchen.aiyagirl.R

/**
 * Dialog父类
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  16:28
 */

abstract class BaseDialog
@JvmOverloads constructor(
        private val mContext: Context,
        layoutId: Int, styleId: Int = R.style.MyDialog)
    : Dialog(mContext, styleId) {

    init {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(layoutId, null)
        this.setContentView(view)
//        ButterKnife.bind(this)
    }
}
