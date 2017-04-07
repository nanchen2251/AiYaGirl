package com.nanchen.aiyagirl.widget.RecyclerViewWithFooter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanchen.aiyagirl.R;


/**
 * 默认的空数据视图
 *
 * @author cjj on 16/1/31.
 */
public class DefaultEmptyItem extends EmptyItem {

    private TextView mEmptyTextView;
    private ImageView mEmptyImageView;

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_with_footer_empty_layout, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(parent.getMeasuredWidth(), parent.getMeasuredHeight()));
        mEmptyTextView = (TextView) view.findViewById(R.id.rv_with_footer_empty_title);
        mEmptyImageView = (ImageView) view.findViewById(R.id.rv_with_footer_empty_icon);
        return view;
    }

    @Override
    public void onBindData(View view) {
        if (TextUtils.isEmpty(mEmptyText)) {
            mEmptyTextView.setVisibility(View.GONE);
        } else {
            mEmptyTextView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(mEmptyText);
        }

        if (mEmptyIconRes != -1) {
            mEmptyImageView.setImageResource(mEmptyIconRes);
        }
    }
}
