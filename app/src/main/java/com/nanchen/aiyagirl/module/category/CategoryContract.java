package com.nanchen.aiyagirl.module.category;

import com.nanchen.aiyagirl.base.BasePresenter;
import com.nanchen.aiyagirl.base.BaseView;
import com.nanchen.aiyagirl.model.CategoryResult.ResultsBean;

import java.util.List;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  10:14
 */

public interface CategoryContract {

    interface ICategoryView extends BaseView{
        
        void getCategoryItemsFail(String failMessage);

        void setCategoryItems(List<ResultsBean> data);

        void addCategoryItems(List<ResultsBean> data);

        void showSwipeLoading();

        void hideSwipeLoading();

        void setLoading();

        String getCategoryName();

        void setNoMore();
    }
    
    interface ICategoryPresenter extends BasePresenter{
        
        void getCategoryItems(boolean isRefresh);
    }
}
