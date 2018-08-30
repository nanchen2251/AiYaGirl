package com.nanchen.aiyagirl.module.home

import com.nanchen.aiyagirl.model.CategoryResult
import com.nanchen.aiyagirl.model.PictureModel
import com.nanchen.aiyagirl.model.ResultsBean
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
        if (mSubscription != null && !mSubscription!!.isUnsubscribed) {
            mSubscription!!.unsubscribe()
        }
    }

    override fun getBannerData() {
        mSubscription = NetWork.getGankApi()
                .getCategoryData("福利", 5, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CategoryResult> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        mHomeView.showBannerFail("Banner 图加载失败")
                    }

                    override fun onNext(categoryResult: CategoryResult?) {
                        if (categoryResult != null && !categoryResult.results.isEmpty()) {
                            val imgUrls = ArrayList<String>()
                            for (result: ResultsBean in categoryResult.results) {
                                if (!result.url.isEmpty()) {
                                    imgUrls.add(result.url)
                                }
                                mModels.add(PictureModel(result.desc, result.url))
                            }
                            mHomeView.setBanner(imgUrls)
                        } else {
                            mHomeView.showBannerFail("Banner 图加载失败")
                        }
                    }
                })
    }

}
