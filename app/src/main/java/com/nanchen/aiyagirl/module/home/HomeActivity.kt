package com.nanchen.aiyagirl.module.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.kekstudio.dachshundtablayout.DachshundTabLayout
import com.nanchen.aiyagirl.GlideImageLoader
import com.nanchen.aiyagirl.R
import com.nanchen.aiyagirl.base.BaseActivity
import com.nanchen.aiyagirl.base.adapter.CommonViewPagerAdapter
import com.nanchen.aiyagirl.config.GlobalConfig
import com.nanchen.aiyagirl.module.category.CategoryFragment
import com.nanchen.aiyagirl.module.home.HomeContract.IHomeView
import com.nanchen.aiyagirl.module.navabout.NavAboutActivity
import com.nanchen.aiyagirl.module.navdeedback.NavDeedBackActivity
import com.nanchen.aiyagirl.module.navhome.NavHomeActivity
import com.nanchen.aiyagirl.module.picture.PictureActivity
import com.nanchen.aiyagirl.module.web.WebViewActivity
import com.nanchen.aiyagirl.utils.*
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_nav.view.*


/**
 * 主页面
 *
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  15:31
 */

class HomeActivity : BaseActivity(), IHomeView, OnBannerListener {

    lateinit var mHeadImg: ImageView
    lateinit var mToolbar: Toolbar
    lateinit var mAppbar: AppBarLayout
    lateinit var mTabLayout: DachshundTabLayout
    lateinit var mViewPager: ViewPager
    lateinit var mNavView: NavigationView
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mBanner: Banner
    lateinit var mFab: FloatingActionButton
    // 保存用户按返回键的时间
    private var mExitTime: Long = 0
    private lateinit var mHomePresenter: HomePresenter


    override val contentViewLayoutID: Int
        get() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        mHeadImg = main_head_img
        mToolbar = main_toolbar
        mAppbar = main_appbar
        mTabLayout = main_tab
        mViewPager = main_vp
        mNavView = nav_view
        mDrawerLayout = mainActivity
        mBanner = main_banner
        mFab = main_fab

        mHomePresenter = HomePresenter(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4 以上版本
            // 设置 Toolbar 高度为 80dp，适配状态栏
            val layoutParams = mToolbar.layoutParams
            layoutParams.height = ScreenUtil.dip2px(this, 80f)
            mToolbar.layoutParams = layoutParams
        }

        // 华为底部导航栏适配
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content))
        }

        initDrawerLayout()

        mBanner.setIndicatorGravity(BannerConfig.RIGHT)
        mBanner.setOnBannerListener(this)

        val titles = arrayOf(GlobalConfig.CATEGORY_NAME_APP, GlobalConfig.CATEGORY_NAME_ANDROID, GlobalConfig.CATEGORY_NAME_IOS, GlobalConfig.CATEGORY_NAME_FRONT_END, GlobalConfig.CATEGORY_NAME_RECOMMEND, GlobalConfig.CATEGORY_NAME_RESOURCE)

        val infoPagerAdapter = CommonViewPagerAdapter(supportFragmentManager, titles)
        // App
        val appFragment = CategoryFragment.newInstance(titles[0])
        // Android
        val androidFragment = CategoryFragment.newInstance(titles[1])
        // iOS
        val iOSFragment = CategoryFragment.newInstance(titles[2])
        // 前端
        val frontFragment = CategoryFragment.newInstance(titles[3])
        // 瞎推荐
        val referenceFragment = CategoryFragment.newInstance(titles[4])
        // 拓展资源s
        val resFragment = CategoryFragment.newInstance(titles[5])

        infoPagerAdapter.addFragment(appFragment)
        infoPagerAdapter.addFragment(androidFragment)
        infoPagerAdapter.addFragment(iOSFragment)
        infoPagerAdapter.addFragment(frontFragment)
        infoPagerAdapter.addFragment(referenceFragment)
        infoPagerAdapter.addFragment(resFragment)

        mViewPager.adapter = infoPagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)
        mViewPager.currentItem = 1
        mViewPager.offscreenPageLimit = 6

        mHomePresenter.subscribe()
    }


    private val mListener = object : PerfectClickListener() {
        override fun onNoDoubleClick(v: View) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
            mDrawerLayout.postDelayed({
                when (v.id) {
                    R.id.ll_nav_homepage // 项目主页
                    -> startActivity(Intent(this@HomeActivity, NavHomeActivity::class.java))
                    R.id.ll_nav_scan_address // 关于我们
                    -> startActivity(Intent(this@HomeActivity, NavAboutActivity::class.java))
                    R.id.ll_nav_deedback // 问题反馈
                    -> startActivity(Intent(this@HomeActivity, NavDeedBackActivity::class.java))
                    R.id.ll_nav_donation // 捐赠开发者
                    ->
                        // https://fama.alipay.com/qrcode/qrcodelist.htm?qrCodeType=P  二维码地址
                        // http://cli.im/deqr/ 解析二维码
                        // aex01018hzmxqeqmcaffh96
                        if (AlipayZeroSdk.hasInstalledAlipayClient(this@HomeActivity)) {
                            AlipayZeroSdk.startAlipayClient(this@HomeActivity, "aex01018hzmxqeqmcaffh96")
                        } else {
                            Snackbar.make(mToolbar, "谢谢，您没有安装支付宝客户端", Snackbar.LENGTH_LONG).show()
                        }
                    R.id.ll_nav_login // 登录github账号
                    -> {
                        val intent_login = Intent(this@HomeActivity, WebViewActivity::class.java)
                        intent_login.putExtra(WebViewActivity.GANK_TITLE, "登录github")
                        intent_login.putExtra(WebViewActivity.GANK_URL, "https://github.com/login")
                        startActivity(intent_login)
                    }
                    R.id.ll_nav_exit -> finish()
                    else -> {
                    }
                }
            }, 260)
        }
    }

    override fun beforeInit() {
        super.beforeInit()
        StatusBarUtil.setTranslucent(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mHomePresenter.unSubscribe()
    }

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    /**
     * inflateHeaderView 进来的布局要宽一些
     */
    private fun initDrawerLayout() {
        mNavView.inflateHeaderView(R.layout.layout_main_nav)
        val headerView = mNavView.getHeaderView(0)
        headerView.ll_nav_homepage.setOnClickListener(mListener)
        headerView.ll_nav_scan_address.setOnClickListener(mListener)
        headerView.ll_nav_scan_address.setOnClickListener(mListener)
        headerView.ll_nav_deedback.setOnClickListener(mListener)
        headerView.ll_nav_login.setOnClickListener(mListener)
        headerView.ll_nav_exit.setOnClickListener(mListener)
        headerView.ll_nav_donation.setOnClickListener(mListener)

    }


    override fun onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Snackbar.make(mDrawerLayout, R.string.exit_toast, Toast.LENGTH_SHORT).show()
            mExitTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }

    override fun showBannerFail(failMessage: String) {
        Toasty.error(this, failMessage).show()
    }

    override fun setBanner(imgUrls: List<String>) {
        mBanner.setImages(imgUrls).setImageLoader(GlideImageLoader()).start()
    }


    override fun OnBannerClick(position: Int) {
        val (desc, url) = mHomePresenter.bannerModel[position]
        //        Intent intent = PictureActivity.newIntent(this,model.url,model.desc);
        //        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
        //                this,mBanner,PictureActivity.TRANSIT_PIC);
        //        ActivityCompat.startActivity(this,intent,optionsCompat.toBundle());
        PictureActivity.start(this, url, desc, mBanner)
    }
}
