package com.nanchen.aiyagirl.module.home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.nanchen.aiyagirl.GlideImageLoader;
import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.base.BaseActivity;
import com.nanchen.aiyagirl.base.adapter.CommonViewPagerAdapter;
import com.nanchen.aiyagirl.config.GlobalConfig;
import com.nanchen.aiyagirl.module.category.CategoryFragment;
import com.nanchen.aiyagirl.module.home.HomeContract.IHomePresenter;
import com.nanchen.aiyagirl.module.home.HomeContract.IHomeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


/**
 * 主页面
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  15:31
 */

public class HomeActivity extends BaseActivity implements IHomeView{


    @BindView(R.id.main_head_img)
    ImageView mHeadImg;
    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.main_tab)
    DachshundTabLayout mTabLayout;
    @BindView(R.id.main_vp)
    ViewPager mViewPager;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.mainActivity)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_banner)
    Banner mBanner;
    @BindView(R.id.main_fab)
    FloatingActionButton mFab;
    // 保存用户按返回键的时间
    private long mExitTime = 0;
    private IHomePresenter mHomePresenter;



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mHomePresenter = new HomePresenter(this);


        initDrawerLayout();

        mBanner.setIndicatorGravity(BannerConfig.RIGHT);

        String[] titles = {
                GlobalConfig.CATEGORY_NAME_APP,
                GlobalConfig.CATEGORY_NAME_ANDROID,
                GlobalConfig.CATEGORY_NAME_IOS,
                GlobalConfig.CATEGORY_NAME_FRONT_END,
                GlobalConfig.CATEGORY_NAME_RECOMMEND,
                GlobalConfig.CATEGORY_NAME_RESOURCE};

        CommonViewPagerAdapter infoPagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(),titles);
        // App
        CategoryFragment appFragment = CategoryFragment.newInstance(titles[0]);
        // Android
        CategoryFragment androidFragment = CategoryFragment.newInstance(titles[1]);
        // iOS
        CategoryFragment iOSFragment = CategoryFragment.newInstance(titles[2]);
        // 前端
        CategoryFragment frontFragment = CategoryFragment.newInstance(titles[3]);
        // 瞎推荐
        CategoryFragment referenceFragment = CategoryFragment.newInstance(titles[4]);
        // 拓展资源s
        CategoryFragment resFragment = CategoryFragment.newInstance(titles[5]);

        infoPagerAdapter.addFragment(appFragment);
        infoPagerAdapter.addFragment(androidFragment);
        infoPagerAdapter.addFragment(iOSFragment);
        infoPagerAdapter.addFragment(frontFragment);
        infoPagerAdapter.addFragment(referenceFragment);
        infoPagerAdapter.addFragment(resFragment);

        mViewPager.setAdapter(infoPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
        mViewPager.setOffscreenPageLimit(6);


        mHomePresenter.subscribe();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHomePresenter != null){
            mHomePresenter.unSubscribe();
        }
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * inflateHeaderView 进来的布局要宽一些
     */
    private void initDrawerLayout() {
        mNavView.inflateHeaderView(R.layout.layout_main_nav);
        View headerView = mNavView.getHeaderView(0);

    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Snackbar.make(mDrawerLayout, R.string.exit_toast, Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showBannerFail(String failMessage) {
        Toasty.error(this, failMessage).show();
    }

    @Override
    public void setBanner(List<String> imgUrls) {
        mBanner.setImages(imgUrls).setImageLoader(new GlideImageLoader()).start();
    }

}
