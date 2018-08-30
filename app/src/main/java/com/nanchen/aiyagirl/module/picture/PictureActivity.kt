package com.nanchen.aiyagirl.module.picture

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.github.chrisbanes.photoview.PhotoView
import com.nanchen.aiyagirl.R
import com.nanchen.aiyagirl.base.BaseActivity
import com.nanchen.aiyagirl.module.picture.PictureContract.PictureView
import com.nanchen.aiyagirl.module.picture.PictureContract.Presenter
import com.nanchen.aiyagirl.utils.Utils
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.activity_picture.*


/**
 * 大图页面
 *
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-24  10:11
 */
class PictureActivity : BaseActivity(), PictureView {
    private var mBitmap: Bitmap? = null

    lateinit var mImageUrl: String
    lateinit var mImageTitle: String
    lateinit var mToolbar: Toolbar
    lateinit var mImgView: PhotoView
    lateinit var mProgressBar: ProgressBar
    lateinit var mSaveBtn: ImageButton

    private lateinit var mPresenter: Presenter

    override val contentViewLayoutID: Int
        get() = R.layout.activity_picture

    private fun parseIntent() {
        mImageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
        mImageTitle = intent.getStringExtra(EXTRA_IMAGE_TITLE)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar = picture_toolbar
        mImgView = picture_img
        mProgressBar = picture_progress
        mSaveBtn = picture_btn_save

        showProgress()
        mPresenter = PicturePresenter(this)
        parseIntent()
        ViewCompat.setTransitionName(mImgView, TRANSIT_PIC)

        mToolbar.title = if (TextUtils.isEmpty(mImageTitle)) "图片预览" else mImageTitle
        mToolbar.setNavigationOnClickListener { finish() }
        setSupportActionBar(mToolbar)
        supportActionBar?.hide()

        val target = object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                hideProgress()
                mBitmap = resource
                if (resource != null){
                    mSaveBtn.visibility = View.VISIBLE
                    mImgView.setImageBitmap(resource)
                }else{
                    mSaveBtn.visibility = View.GONE
                }
            }
        }

        Glide.with(Utils.getContext())
                .load(mImageUrl)
                .asBitmap()
                .dontAnimate()
                .into(target)

    }


    @OnClick(R.id.picture_btn_save)
    fun onClick() {
        mPresenter.saveGirl(mImageUrl, mBitmap!!, mImageTitle)
    }


    @OnClick(R.id.picture_img)
    fun onPictureClick() {
        if (supportActionBar != null) {
            if (supportActionBar!!.isShowing) {
                supportActionBar!!.hide()
            } else {
                supportActionBar!!.show()
            }
        }
    }

    override fun onDestroy() {
        System.gc()
        super.onDestroy()
    }

    override fun hideProgress() {
        mProgressBar.visibility = View.GONE
    }

    override fun showProgress() {
        mProgressBar.visibility = View.VISIBLE
    }

    companion object {

        const val EXTRA_IMAGE_URL = "com.nanchen.aiyagirl.module.picture.PictureActivity.EXTRA_IMAGE_URL"
        const val EXTRA_IMAGE_TITLE = "com.nanchen.aiyagirl.module.picture.PictureActivity.EXTRA_IMAGE_TITLE"
        const val TRANSIT_PIC = "picture"

        fun newIntent(context: Context, url: String, desc: String): Intent {
            val intent = Intent(context, PictureActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_URL, url)
            intent.putExtra(EXTRA_IMAGE_TITLE, desc)
            return intent
        }

        fun start(context: Activity, url: String, desc: String, banner: Banner) {
            val intent = Intent(context, PictureActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_URL, url)
            intent.putExtra(EXTRA_IMAGE_TITLE, desc)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context, banner, TRANSIT_PIC)//与xml文件对应
            ActivityCompat.startActivity(context, intent, options.toBundle())
        }
    }
}
