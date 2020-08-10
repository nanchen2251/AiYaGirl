package com.nanchen.aiyagirl.module.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.nanchen.aiyagirl.R
import com.nanchen.aiyagirl.base.BaseActivity
import com.nanchen.aiyagirl.config.ConstantsImageUrl
import com.nanchen.aiyagirl.module.home.HomeActivity
import com.nanchen.aiyagirl.utils.ImageUrls
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


    override val contentViewLayoutID: Int= R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {


        val i = Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.size)
        // 先显示默认图

        splash_iv_defult_pic.setImageDrawable(  ContextCompat.getDrawable(this, R.drawable.img_transition_default))
        Glide.with(this)
                .load(ImageUrls.instance.getRandomImgUrl())
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
