package com.nanchen.aiyagirl.module.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 主页面
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  15:31
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.mainActivity)
    DrawerLayout mMainActivity;
    @BindView(R.id.view_status)
    View mViewStatus;
    @BindView(R.id.iv_title_menu)
    ImageView mIvTitleMenu;
    @BindView(R.id.ll_title_menu)
    FrameLayout mLlTitleMenu;
    @BindView(R.id.iv_title_gank)
    ImageView mIvTitleGank;
    @BindView(R.id.iv_title_one)
    ImageView mIvTitleOne;
    @BindView(R.id.iv_title_dou)
    ImageView mIvTitleDou;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.nav_view)
    NavigationView mNavView;


    // 保存用户按返回键的时间
    private long mExitTime = 0;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        initDrawerLayout();
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
            Snackbar.make(mMainActivity, R.string.exit_toast, Toast.LENGTH_SHORT).show();
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
}
