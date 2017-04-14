package com.nanchen.aiyagirl.module.category;

import com.nanchen.aiyagirl.config.GlobalConfig;
import com.nanchen.aiyagirl.model.CategoryResult;
import com.nanchen.aiyagirl.module.category.CategoryContract.ICategoryView;
import com.nanchen.aiyagirl.module.category.CategoryContract.ICategoryPresenter;
import com.nanchen.aiyagirl.net.NetWork;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ICategoryPresenter
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  11:16
 */

public class CategoryPresenter implements ICategoryPresenter {

    private ICategoryView mCategoryICategoryView;
    private int mPage = 1;
    private Subscription mSubscription;

    public CategoryPresenter(ICategoryView androidICategoryView) {
        mCategoryICategoryView = androidICategoryView;
    }

    @Override
    public void subscribe() {
        getCategoryItems(true);
    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null  && !mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void getCategoryItems(final boolean isRefresh) {
        if (isRefresh) {
            mPage = 1;
            mCategoryICategoryView.showSwipeLoading();
        } else {
            mPage++;
        }
        mSubscription = NetWork.getGankApi()
                .getCategoryData(mCategoryICategoryView.getCategoryName(), GlobalConfig.CATEGORY_COUNT,mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mCategoryICategoryView.hideSwipeLoading();
                        mCategoryICategoryView.getCategoryItemsFail(mCategoryICategoryView.getCategoryName()+" 列表数据获取失败！");
                    }

                    @Override
                    public void onNext(CategoryResult categoryResult) {
                        if (isRefresh){
                            mCategoryICategoryView.setCategoryItems(categoryResult);
                            mCategoryICategoryView.hideSwipeLoading();
                            mCategoryICategoryView.setLoading();
                        }else {
                            mCategoryICategoryView.addCategoryItems(categoryResult);
                        }
                    }
                });

    }
}
