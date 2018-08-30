package com.nanchen.aiyagirl.module.home

import com.nanchen.aiyagirl.base.BasePresenter
import com.nanchen.aiyagirl.base.BaseView

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  12:28
 */

interface HomeContract {
    interface IHomeView : BaseView {
        fun showBannerFail(failMessage: String)

        fun setBanner(imgUrls: List<String>)

    }

    interface IHomePresenter : BasePresenter {
        /**
         * 获取Banner轮播图数据
         */
        fun getBannerData()

    }
}
