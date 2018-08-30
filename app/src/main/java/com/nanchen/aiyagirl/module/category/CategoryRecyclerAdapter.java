package com.nanchen.aiyagirl.module.category;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nanchen.aiyagirl.ConfigManage;
import com.nanchen.aiyagirl.R;
import com.nanchen.aiyagirl.base.adapter.CommonRecyclerAdapter;
import com.nanchen.aiyagirl.base.adapter.CommonRecyclerHolder;
import com.nanchen.aiyagirl.base.adapter.ListenerWithPosition;
import com.nanchen.aiyagirl.model.ResultsBean;
import com.nanchen.aiyagirl.module.web.WebViewActivity;
import com.nanchen.aiyagirl.utils.TimeUtil;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-14  10:21
 */

class CategoryRecyclerAdapter extends CommonRecyclerAdapter<ResultsBean> implements ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder> {

    CategoryRecyclerAdapter(Context context) {
        super(context, null, R.layout.item_category);
    }

    @Override
    public void convert(CommonRecyclerHolder holder, ResultsBean resultsBean) {
        if (resultsBean != null) {
            ImageView imageView = holder.getView(R.id.category_item_img);
            if (ConfigManage.INSTANCE.isListShowImg()) { // 列表显示图片
                imageView.setVisibility(View.VISIBLE);
                String quality = "";
                if (resultsBean.getImages() != null && resultsBean.getImages().size() > 0) {
                    switch (ConfigManage.INSTANCE.getThumbnailQuality()) {
                        case 0: // 原图
                            quality = "";
                            break;
                        case 1: //
                            quality = "?imageView2/0/w/400";
                            break;
                        case 2:
                            quality = "?imageView2/0/w/190";
                            break;
                    }
                    Glide.with(mContext)
                            .load(resultsBean.getImages().get(0) + quality)
                            .placeholder(R.mipmap.image_default)
                            .error(R.mipmap.image_default)
                            .into(imageView);
                } else { // 列表不显示图片
                    Glide.with(mContext).load(R.mipmap.image_default).into(imageView);
                }
            } else {
                imageView.setVisibility(View.GONE);
            }

            holder.setTextViewText(R.id.category_item_desc, TextUtils.isEmpty(resultsBean.getDesc()) ? "unknown" : resultsBean.getDesc());
            holder.setTextViewText(R.id.category_item_author, TextUtils.isEmpty(resultsBean.getWho()) ? "unknown" : resultsBean.getWho());
            holder.setTextViewText(R.id.category_item_time, TimeUtil.dateFormat(resultsBean.getPublishedAt()));
            holder.setTextViewText(R.id.category_item_src, TextUtils.isEmpty(resultsBean.getSource()) ? "unknown" : resultsBean.getSource());
            holder.setOnClickListener(this, R.id.category_item_layout);
        }
    }

    @Override
    public void onClick(View v, int position, CommonRecyclerHolder holder) {
//        Toasty.info(mContext,"跳转到相应网页！", Toast.LENGTH_SHORT,true).show();
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(WebViewActivity.GANK_TITLE, mData.get(position).getDesc());
        intent.putExtra(WebViewActivity.GANK_URL, mData.get(position).getUrl());
        mContext.startActivity(intent);
    }
}
