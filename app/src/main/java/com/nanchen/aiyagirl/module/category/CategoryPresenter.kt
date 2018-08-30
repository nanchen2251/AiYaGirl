package com.nanchen.aiyagirl.module.category

import com.nanchen.aiyagirl.config.GlobalConfig
import com.nanchen.aiyagirl.model.CategoryResult
import com.nanchen.aiyagirl.net.NetWork
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * ICategoryPresenter
 *
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  11:16
 */

class CategoryPresenter(
        private val mCategoryICategoryView: ICategoryView)
    : ICategoryPresenter {

    private var mPage = 1
    private var mSubscription: Subscription? = null

    override fun subscribe() {
        getCategoryItems(true)
    }

    override fun unSubscribe() {
        mSubscription?.let {
            if (!mSubscription!!.isUnsubscribed) {
                mSubscription!!.unsubscribe()
            }
        }
    }

    override fun getCategoryItems(isRefresh: Boolean) {
        if (isRefresh) {
            mPage = 1
            mCategoryICategoryView.showSwipeLoading()
        } else {
            mPage++
        }
        mSubscription = NetWork.getGankApi()
                .getCategoryData(mCategoryICategoryView.categoryName, GlobalConfig.CATEGORY_COUNT, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CategoryResult> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        mCategoryICategoryView.hideSwipeLoading()
                        mCategoryICategoryView.getCategoryItemsFail(mCategoryICategoryView.categoryName + " 列表数据获取失败！")
                    }

                    override fun onNext(categoryResult: CategoryResult?) {
                        if (categoryResult != null && !categoryResult.error) {
                            if (categoryResult.results.isEmpty()) {
                                // 如果可以，这里可以增加占位图
                                mCategoryICategoryView.getCategoryItemsFail("获取数据为空！")
                            } else {
                                if (isRefresh) {
                                    mCategoryICategoryView.setCategoryItems(categoryResult.results)
                                    mCategoryICategoryView.hideSwipeLoading()
                                    mCategoryICategoryView.setLoading()
                                } else {
                                    mCategoryICategoryView.addCategoryItems(categoryResult.results)
                                }
                                // 如果当前获取的数据数目没有全局设定的每次获取的条数，说明已经没有更多数据
                                if (categoryResult.results.size < GlobalConfig.CATEGORY_COUNT) {
                                    mCategoryICategoryView.setNoMore()
                                }
                            }
                        } else {
                            mCategoryICategoryView.getCategoryItemsFail("获取数据失败！")
                        }

                    }
                })

    }
}
