package com.nanchen.aiyagirl.module.navabout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.base.BaseActivity;
import com.nanchen.aiyagirl.module.web.WebViewActivity;
import com.nanchen.aiyagirl.utils.PackageUtil;
import com.nanchen.aiyagirl.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class NavAboutActivity extends BaseActivity {

    @BindView(R.id.nav_about_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_version_name)
    TextView mTvVersionName;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_nav_about;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvVersionName.setText("当前版本 V"+PackageUtil.getVersionName());
    }


    @OnClick({R.id.tv_new_version, R.id.tv_function, R.id.tv_about_star, R.id.tv_gankio})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_new_version:
                Utils.openLink(this,getResources().getString(R.string.string_url_new_version));
                break;
            case R.id.tv_function:
//                Utils.openLink(this,getResources().getString(R.string.string_url_other));
                Intent intentOther = new Intent(this, WebViewActivity.class);
                intentOther.putExtra(WebViewActivity.GANK_TITLE,"其他开源");
                intentOther.putExtra(WebViewActivity.GANK_URL,getResources().getString(R.string.string_url_other));
                startActivity(intentOther);
                break;
            case R.id.tv_about_star:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.GANK_TITLE,"给个Star吧");
                intent.putExtra(WebViewActivity.GANK_URL,getResources().getString(R.string.string_url_AiYaGirl));
                startActivity(intent);
                break;
            case R.id.tv_gankio:
                Intent intent1 = new Intent(this, WebViewActivity.class);
                intent1.putExtra(WebViewActivity.GANK_TITLE,"拿着Api去玩耍");
                intent1.putExtra(WebViewActivity.GANK_URL,getResources().getString(R.string.string_url_gankio));
                startActivity(intent1);
                break;
        }
    }
}
