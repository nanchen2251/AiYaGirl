package com.nanchen.aiyagirl.base

import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import butterknife.ButterKnife
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.nanchen.aiyagirl.R
import com.nanchen.aiyagirl.utils.StatusBarUtil
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Activity基类，所有Activity应该继承此类
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  14:33
 */

abstract class BaseActivity : AppCompatActivity(), BGASwipeBackHelper.Delegate {

    private var mCompositeSubscription: CompositeSubscription? = null

    private lateinit var mSwipeBackHelper: BGASwipeBackHelper

    /**
     * 获取布局ID
     *
     * @return  布局id
     */
    protected abstract val contentViewLayoutID: Int

    val compositeSubscription: CompositeSubscription?
        get() {
            checkSubscription()
            return this.mCompositeSubscription
        }


    /**
     * 界面初始化前期准备
     */
    protected open fun beforeInit() {

    }

    /**
     * 初始化布局以及View控件
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        ButterKnife.bind(this)
    }

    /**
     * 检查是否为空，以免导致空指针
     */
    private fun checkSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }
    }

    /**
     * 增加一个调度器
     */
    protected fun addSubscription(s: Subscription) {
        checkSubscription()
        this.mCompositeSubscription!!.add(s)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish()
        //        setStatusBarTransparent();
        super.onCreate(savedInstanceState)
        beforeInit()
        if (contentViewLayoutID != 0) {
            setContentView(contentViewLayoutID)
            initView(savedInstanceState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.mCompositeSubscription != null && !this.mCompositeSubscription!!.isUnsubscribed) {
            this.mCompositeSubscription!!.unsubscribe()
        }
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private fun initSwipeBackFinish() {
        mSwipeBackHelper = BGASwipeBackHelper(this, this)

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true)
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true)
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true)
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow)
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true)
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true)
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f)
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    override fun isSupportSwipeBack(): Boolean {
        return true
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    override fun onSwipeBackLayoutSlide(slideOffset: Float) {}

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    override fun onSwipeBackLayoutCancel() {}

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    override fun onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward()
    }

    override fun onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding) {
            return
        }
        mSwipeBackHelper.backward()
    }


    fun setStatusBarTransparent() {
        StatusBarUtil.setTransparent(this)
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected fun setStatusBarColor(@ColorInt color: Int) {
        setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param statusBarAlpha 透明度
     */
    fun setStatusBarColor(@ColorInt color: Int, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        StatusBarUtil.setColorForSwipeBack(this, color, statusBarAlpha)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
