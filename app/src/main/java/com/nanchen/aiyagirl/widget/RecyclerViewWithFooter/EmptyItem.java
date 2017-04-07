package com.nanchen.aiyagirl.widget.RecyclerViewWithFooter;

import android.view.View;
import android.view.ViewGroup;

/**
 * 没数据时候的默认View
 *
 * @author zzz40500 on 16/1/31.
 */
public abstract class EmptyItem {

    CharSequence mEmptyText;
    int mEmptyIconRes = -1;

    abstract View onCreateView(ViewGroup parent);

    abstract void onBindData(View view);
}
