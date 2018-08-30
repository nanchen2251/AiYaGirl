package com.nanchen.aiyagirl.module.category

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.nanchen.aiyagirl.ConfigManage
import com.nanchen.aiyagirl.R
import com.nanchen.aiyagirl.base.adapter.CommonRecyclerAdapter
import com.nanchen.aiyagirl.base.adapter.CommonRecyclerHolder
import com.nanchen.aiyagirl.base.adapter.ListenerWithPosition
import com.nanchen.aiyagirl.model.ResultsBean
import com.nanchen.aiyagirl.module.web.WebViewActivity
import com.nanchen.aiyagirl.utils.TimeUtil

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  10:21
 */

class CategoryRecyclerAdapter(context: Context)
    : CommonRecyclerAdapter<ResultsBean>(context, null, R.layout.item_category),
        ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder> {

    override fun convert(holder: CommonRecyclerHolder, resultsBean: ResultsBean?) {
        if (resultsBean != null) {
            val imageView = holder.getView<ImageView>(R.id.category_item_img)
            if (ConfigManage.INSTANCE.isListShowImg()) { // 列表显示图片
                imageView.visibility = View.VISIBLE
                var quality = ""
                if (resultsBean.images != null && resultsBean.images!!.isNotEmpty()) {
                    when (ConfigManage.INSTANCE.getThumbnailQuality()) {
                        0 // 原图
                        -> quality = ""
                        1 //
                        -> quality = "?imageView2/0/w/400"
                        2 -> quality = "?imageView2/0/w/190"
                    }
                    Glide.with(mContext)
                            .load(resultsBean.images!![0] + quality)
                            .placeholder(R.mipmap.image_default)
                            .error(R.mipmap.image_default)
                            .into(imageView)
                } else { // 列表不显示图片
                    Glide.with(mContext).load(R.mipmap.image_default).into(imageView)
                }
            } else {
                imageView.visibility = View.GONE
            }

            holder.setTextViewText(R.id.category_item_desc, if (TextUtils.isEmpty(resultsBean.desc)) "unknown" else resultsBean.desc)
            holder.setTextViewText(R.id.category_item_author, if (TextUtils.isEmpty(resultsBean.who)) "unknown" else resultsBean.who)
            holder.setTextViewText(R.id.category_item_time, TimeUtil.dateFormat(resultsBean.publishedAt))
            holder.setTextViewText(R.id.category_item_src, if (TextUtils.isEmpty(resultsBean.source)) "unknown" else resultsBean.source)
            holder.setOnClickListener(this, R.id.category_item_layout)
        }
    }

    override fun onClick(v: View, position: Int, holder: CommonRecyclerHolder) {
        val intent = Intent(mContext, WebViewActivity::class.java)
        intent.putExtra(WebViewActivity.GANK_TITLE, mData[position].desc)
        intent.putExtra(WebViewActivity.GANK_URL, mData[position].url)
        mContext.startActivity(intent)
    }
}
