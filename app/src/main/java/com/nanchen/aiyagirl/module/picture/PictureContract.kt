package com.nanchen.aiyagirl.module.picture

import android.graphics.Bitmap

import com.nanchen.aiyagirl.base.BasePresenter
import com.nanchen.aiyagirl.base.BaseView

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-24  14:30
 */

interface PictureContract {

    interface PictureView : BaseView {
        fun hideProgress()
        fun showProgress()
    }

    interface Presenter : BasePresenter {
        fun saveGirl(url: String, bitmap: Bitmap, title: String)
    }
}
