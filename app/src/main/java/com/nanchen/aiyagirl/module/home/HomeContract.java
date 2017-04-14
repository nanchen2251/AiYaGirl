package com.nanchen.aiyagirl.module.home;

import com.nanchen.aiyagirl.base.BasePresenter;
import com.nanchen.aiyagirl.base.BaseView;

import java.util.List;

/**
 *
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  12:28
 */

public interface HomeContract {
    interface IHomeView extends BaseView{
        void showBannerFail(String failMessage);

        void setBanner(List<String> imgUrls);
    }

    interface IHomePresenter extends BasePresenter{
        /**
         * 获取Banner轮播图数据
         */
        void getBannerData();

    }
}
