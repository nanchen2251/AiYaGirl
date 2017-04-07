package com.nanchen.aiyagirl.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.base.BaseActivity;
import com.nanchen.aiyagirl.config.ConstantsImageUrl;
import com.nanchen.aiyagirl.module.home.MainActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 闪屏页面
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  14:59
 */

public class SplashActivity extends BaseActivity {
    private boolean isIn;

    @BindView(R.id.splash_iv_pic)
    ImageView mIvPic;
    @BindView(R.id.splash_tv_jump)
    TextView mTvJump;
    @BindView(R.id.splash_iv_defult_pic)
    ImageView mIvDefultPic;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
        // 先显示默认图
        mIvDefultPic.setImageDrawable(getResources().getDrawable(R.drawable.img_transition_default));
        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.drawable.img_transition_default)
                .error(R.drawable.img_transition_default)
                .into(mIvPic);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mIvDefultPic.setVisibility(View.GONE);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 3500);
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 跳转到主页面
     */
    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }



    @OnClick(R.id.splash_tv_jump)
    public void onClick() {
        toMainActivity();
    }
}
