package com.nanchen.aiyagirl.base.adapter;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * 通用的RecyclerView的Holder
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  16:30
 */

public class CommonRecyclerHolder extends RecyclerView.ViewHolder {
    public View mConvertView;
    public int position;
    private SparseArray<View> mViews;

    public CommonRecyclerHolder(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }


    /**
     * 得到item上的控件
     *
     * @param viewId 控件的id
     * @return 对应的控件
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;

    }

    public CommonRecyclerHolder setTextViewText(@IdRes int textViewId, String text) {
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText(" ");
        }
        return this;
    }

    public CommonRecyclerHolder setOnClickListener(ListenerWithPosition.OnClickWithPositionListener clickListener, @IdRes int... viewIds) {
        ListenerWithPosition listener = new ListenerWithPosition(position, this);
        listener.setOnClickListener(clickListener);
        for (int id : viewIds) {
            View v = getView(id);
            v.setOnClickListener(listener);
        }
        return this;
    }
}
