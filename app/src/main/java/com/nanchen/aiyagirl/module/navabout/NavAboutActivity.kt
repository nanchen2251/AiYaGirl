package com.nanchen.aiyagirl.module.navabout

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import butterknife.OnClick
import com.nanchen.aiyagirl.R
import com.nanchen.aiyagirl.base.BaseActivity
import com.nanchen.aiyagirl.module.web.WebViewActivity
import com.nanchen.aiyagirl.utils.PackageUtil
import com.nanchen.aiyagirl.utils.Utils
import kotlinx.android.synthetic.main.activity_nav_about.*

class NavAboutActivity : BaseActivity() {

    lateinit var mToolbar: Toolbar
    lateinit var mTvVersionName: TextView

    override val contentViewLayoutID: Int
        get() = R.layout.activity_nav_about


    override fun initView(savedInstanceState: Bundle?) {
        mToolbar = nav_about_toolbar
        mTvVersionName = tv_version_name

        mToolbar.setNavigationOnClickListener { finish() }
        mTvVersionName.text = "当前版本 V${PackageUtil.getVersionName()}"
    }


    @OnClick(R.id.tv_new_version, R.id.tv_function, R.id.tv_about_star, R.id.tv_gankio)
    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_new_version -> Utils.openLink(this, resources.getString(R.string.string_url_new_version))
            R.id.tv_function -> {
                val intentOther = Intent(this, WebViewActivity::class.java)
                intentOther.putExtra(WebViewActivity.GANK_TITLE, "其他开源")
                intentOther.putExtra(WebViewActivity.GANK_URL, resources.getString(R.string.string_url_other))
                startActivity(intentOther)
            }
            R.id.tv_about_star -> {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.GANK_TITLE, "给个Star吧")
                intent.putExtra(WebViewActivity.GANK_URL, resources.getString(R.string.string_url_AiYaGirl))
                startActivity(intent)
            }
            R.id.tv_gankio -> {
                val intent1 = Intent(this, WebViewActivity::class.java)
                intent1.putExtra(WebViewActivity.GANK_TITLE, "拿着Api去玩耍")
                intent1.putExtra(WebViewActivity.GANK_URL, resources.getString(R.string.string_url_gankio))
                startActivity(intent1)
            }
        }
    }
}
