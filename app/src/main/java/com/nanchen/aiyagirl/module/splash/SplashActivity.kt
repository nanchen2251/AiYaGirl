package com.nanchen.aiyagirl.module.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.nanchen.aiyagirl.R
import com.nanchen.aiyagirl.base.BaseActivity
import com.nanchen.aiyagirl.config.ConstantsImageUrl
import com.nanchen.aiyagirl.module.home.HomeActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

/**
 * 闪屏页面
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  14:59
 */

class SplashActivity : BaseActivity() {
    private var isIn: Boolean = false

    @BindView(R.id.splash_tv_jump)
    private lateinit var mTvJump: TextView

    override val contentViewLayoutID: Int
        get() = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {


        val i = Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.size)
        // 先显示默认图

        splash_iv_defult_pic.setImageDrawable(resources.getDrawable(R.drawable.img_transition_default))
        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .error(R.drawable.img_transition_default)
                .into(splash_iv_pic)
        Handler().postDelayed({ splash_iv_defult_pic.visibility = View.GONE }, 1500)

        Handler().postDelayed({ toMainActivity() }, 3500)
        splash_tv_jump.setOnClickListener { toMainActivity() }
    }

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    /**
     * 跳转到主页面
     */
    private fun toMainActivity() {
        if (isIn) {
            return
        }
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
        finish()
        isIn = true
    }

}
