package com.nanchen.aiyagirl.module.navdeedback;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.base.BaseActivity;
import com.nanchen.aiyagirl.module.web.WebViewActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 问题反馈页面
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-20  10:10
 */
public class NavDeedBackActivity extends BaseActivity {

    @BindView(R.id.nav_deed_back_toolbar)
    Toolbar mToolbar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_nav_deed_back;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick({R.id.tv_issues, R.id.tv_other, R.id.tv_qq, R.id.tv_email, R.id.tv_blog})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_issues:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.GANK_TITLE, "爱吖妹纸");
                intent.putExtra(WebViewActivity.GANK_URL, "https://github.com/nanchen2251/AiYaGirl");
                startActivity(intent);
                break;
            case R.id.tv_other:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.GANK_TITLE, "nanchen2251");
                intent.putExtra(WebViewActivity.GANK_URL, "https://github.com/nanchen2251");
                startActivity(intent);
                break;
            case R.id.tv_qq:
                if (isQQClientAvailable()) {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=503233512";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } else
                    Toasty.error(this, "当前设备未安装QQ").show();
                break;
            case R.id.tv_email:
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:liushilin520@foxmail.com"));
                startActivity(intent);
                break;
            case R.id.tv_blog:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.GANK_TITLE, "博客园");
                intent.putExtra(WebViewActivity.GANK_URL, "http://www.cnblogs.com/liushilin/");
                startActivity(intent);
                break;
        }
    }

    /**
     * 判断qq是否可用
     */
    public static boolean isQQClientAvailable() {
        final PackageManager packageManager = Utils.getContext().getPackageManager();
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);
        if (packageInfo != null) {
            for (int i = 0; i < packageInfo.size(); i++) {
                String pn = packageInfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
}
