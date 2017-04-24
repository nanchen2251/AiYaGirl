package com.nanchen.aiyagirl.module.picture;

import com.nanchen.aiyagirl.base.BasePresenter;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-24  14:30
 */

public interface PictureContract {

    interface Presenter extends BasePresenter{
        void saveGirl(String url, int width,int height,String title);
    }
}
