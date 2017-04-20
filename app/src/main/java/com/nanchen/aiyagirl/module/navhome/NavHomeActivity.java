package com.nanchen.aiyagirl.module.navhome;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.base.BaseActivity;
import com.nanchen.aiyagirl.utils.ShareUtil;
import com.nanchen.aiyagirl.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 项目主页面
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-20  10:10
 */
public class NavHomeActivity extends BaseActivity {

    @BindView(R.id.nav_home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_home_fab)
    FloatingActionButton mFab;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_nav_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolbar);
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.nav_home_fab)
    public void onClick() {
        ShareUtil.share(this, R.string.string_share_text);
    }
}
