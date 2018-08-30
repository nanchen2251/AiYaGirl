package com.nanchen.aiyagirl.module.navhome

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import butterknife.OnClick
import com.nanchen.aiyagirl.R
import com.nanchen.aiyagirl.base.BaseActivity
import com.nanchen.aiyagirl.utils.ShareUtil
import com.nanchen.aiyagirl.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_nav_home.*


/**
 * 项目主页面
 *
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-20  10:10
 */
class NavHomeActivity : BaseActivity() {

    lateinit var mToolbar: Toolbar
    lateinit var mFab: FloatingActionButton

    override val contentViewLayoutID: Int
        get() = R.layout.activity_nav_home

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar = nav_home_toolbar
        mFab = nav_home_fab

        StatusBarUtil.setTranslucentForImageView(this, 0, mToolbar)
        mToolbar.setNavigationOnClickListener { finish() }
        setSupportActionBar(mToolbar)
        //不添加这句华为手机会出现标题显示不完整的问题
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @OnClick(R.id.nav_home_fab)
    fun onClick() {
        ShareUtil.share(this, R.string.string_share_text)
    }
}
