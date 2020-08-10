package com.nanchen.aiyagirl.module.home

import com.nanchen.aiyagirl.model.*
import com.nanchen.aiyagirl.module.home.HomeContract.IHomePresenter
import com.nanchen.aiyagirl.module.home.HomeContract.IHomeView
import com.nanchen.aiyagirl.net.NetWork
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  12:31
 */

class HomePresenter internal constructor(private val mHomeView: IHomeView) : IHomePresenter {

    private var mSubscription: Subscription? = null

    private val mModels: MutableList<PictureModel>

    val bannerModel: List<PictureModel>
        get() = this.mModels

    init {
        mModels = ArrayList()
    }

    override fun subscribe() {
        getBannerData()
    }

    override fun unSubscribe() {
        mSubscription?.let {
            if (!mSubscription!!.isUnsubscribed) {
                mSubscription!!.unsubscribe()
            }
        }

    }

    override fun getBannerData() {
        mSubscription = NetWork.getGankApi()
                .getBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BannerResult> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        mHomeView.showBannerFail("Banner 图加载失败")
                    }

                    override fun onNext(categoryResult: BannerResult?) {
                        if (categoryResult != null && categoryResult.data.isNotEmpty()) {
                            val imgUrls = ArrayList<String>()
                            for (result: Banner in categoryResult.data) {
                                if (result.image.isNotEmpty()) {
                                    imgUrls.add(result.image)
                                }
                                mModels.add(PictureModel(if (result.title.isEmpty()) "unknown" else result.title, result.image))
                            }
                            mHomeView.setBanner(imgUrls)
                        } else {
                            mHomeView.showBannerFail("Banner 图加载失败")
                        }
                    }
                })
    }

}
