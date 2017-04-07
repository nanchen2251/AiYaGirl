package com.nanchen.aiyagirl.widget.RecyclerViewWithFooter;


import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A {@link RecyclerView} with footer which enables load more.
 *
 * @author cjj
 */
public class RecyclerViewWithFooter extends RecyclerView {

    private static final String TAG = "RecyclerViewWithFooter";

    public static final int STATE_END = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_NONE = 3;
    public static final int STATE_PULL_TO_LOAD = 4;

    private boolean mIsGetDataForNet = false;

    @State
    private int mState = STATE_NONE;

    /**
     * 默认的 FootItem;
     */
    private FootItem mFootItem = new DefaultFootItem();
    private EmptyItem mEmptyItem = new DefaultEmptyItem();

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            reset();
        }

        private void reset() {
            mIsGetDataForNet = false;
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            reset();

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            reset();

        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            reset();

        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            reset();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            reset();
        }
    };

    public RecyclerViewWithFooter(Context context) {
        super(context);
        init();
    }

    public RecyclerViewWithFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerViewWithFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setVerticalLinearLayout();
    }

    public void setVerticalLinearLayout() {
        RecyclerViewUtils.setVerticalLinearLayout(this);
    }

    public void setGridLayout(int span) {
        RecyclerViewUtils.setGridLayout(this, span);
    }

    public void setStaggeredGridLayoutManager(int spanCount) {
        RecyclerViewUtils.setStaggeredGridLayoutManager(this, spanCount);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mState = STATE_PULL_TO_LOAD;

        final OnLoadMoreListenerWrapper wrapper = new OnLoadMoreListenerWrapper(onLoadMoreListener);

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        int lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        if (lastVisiblePosition >= recyclerView.getAdapter().getItemCount() - 1) {
                            if (mState == STATE_PULL_TO_LOAD) {
                                setLoading();
                            }
                            wrapper.onLoadMore();
                        }
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        int last[] = new int[staggeredGridLayoutManager.getSpanCount()];
                        staggeredGridLayoutManager.findLastVisibleItemPositions(last);

                        for (int aLast : last) {
                            Log.i(TAG, aLast + "    " + recyclerView.getAdapter().getItemCount());
                            if (aLast >= recyclerView.getAdapter().getItemCount() - 1) {
                                if (mState == STATE_PULL_TO_LOAD) {
                                    setLoading();
                                }
                                wrapper.onLoadMore();
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        LoadMoreAdapter loadMoreAdapter;
        if (adapter instanceof LoadMoreAdapter) {
            loadMoreAdapter = (LoadMoreAdapter) adapter;
            loadMoreAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(adapter);
        } else {
            loadMoreAdapter = new LoadMoreAdapter(adapter);
            loadMoreAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(loadMoreAdapter);
        }
    }

    /**
     * 设置loading提示字符串
     *
     * @param loadText 提示字符串
     * @return {@link RecyclerViewWithFooter}
     */
    public RecyclerViewWithFooter applyLoadingText(CharSequence loadText) {
        mFootItem.loadingText = loadText;
        return this;
    }

    public RecyclerViewWithFooter applyPullToLoadText(CharSequence pullToLoadText) {
        mFootItem.pullToLoadText = pullToLoadText;
        return this;
    }

    public RecyclerViewWithFooter applyEndText(CharSequence endText) {
        mFootItem.endText = endText;
        return this;
    }

    public RecyclerViewWithFooter applyEmptyText(CharSequence emptyText, @DrawableRes int drawableId) {
        mEmptyItem.mEmptyIconRes = drawableId;
        mEmptyItem.mEmptyText = emptyText;
        return this;
    }

    public void setFootItem(FootItem footItem) {
        if (mFootItem != null) {
            if (footItem.endText == null) {
                footItem.endText = mFootItem.endText;
            }
            if (footItem.loadingText == null) {
                footItem.loadingText = mFootItem.loadingText;
            }
            if (footItem.pullToLoadText == null) {
                footItem.pullToLoadText = mFootItem.pullToLoadText;
            }
        }
        mFootItem = footItem;
    }

    public void setEmptyItem(EmptyItem emptyItem) {
        if (mEmptyItem != null) {
            if (emptyItem.mEmptyIconRes == -1) {
                emptyItem.mEmptyIconRes = mEmptyItem.mEmptyIconRes;
            }
            if (emptyItem.mEmptyText == null) {
                emptyItem.mEmptyText = mEmptyItem.mEmptyText;
            }
        }
        mEmptyItem = emptyItem;
    }

    /**
     * 切换为loading状态
     */
    public void setLoading() {
        if (getAdapter() != null) {
            mState = STATE_LOADING;
            mIsGetDataForNet = false;
            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
    }

    /**
     * 切换为没有更多数据状态
     *
     * @param end 提示字符串
     */
    public void setEnd(CharSequence end) {
        if (getAdapter() != null) {
            mIsGetDataForNet = false;
            mState = STATE_END;
            mFootItem.endText = end;
            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
    }

    /**
     * 切换为没有更多数据状态
     */
    public void setEnd() {
        if (getAdapter() != null) {
            mIsGetDataForNet = false;
            mState = STATE_END;
            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
    }

    /**
     * 切换成无数据状态
     *
     * @param empty 无数据状态提示消息
     * @param resId 无数据状态提示图标
     */
    public void setEmpty(CharSequence empty, @DrawableRes int resId) {
        if (getAdapter() != null) {
            mState = STATE_EMPTY;
            mEmptyItem.mEmptyText = empty;
            mEmptyItem.mEmptyIconRes = resId;
            if (isEmpty()) {
                getAdapter().notifyDataSetChanged();
            }
        }
    }

    /**
     * 切换成无数据状态
     */
    public void setEmpty() {
        if (getAdapter() != null) {
            mState = STATE_EMPTY;
            if (isEmpty()) {
                getAdapter().notifyDataSetChanged();
            }
        }
    }

    /**
     * 数据是否为空
     */
    private boolean isEmpty() {
        return (mState == STATE_NONE && getAdapter().getItemCount() == 0) ||
                (mState != STATE_NONE && getAdapter().getItemCount() == 1);
    }

    public boolean isLoadMoreEnable() {
        return mState != STATE_LOADING;
    }

    @IntDef({STATE_END, STATE_LOADING, STATE_EMPTY, STATE_NONE, STATE_PULL_TO_LOAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    private class OnLoadMoreListenerWrapper implements OnLoadMoreListener {

        private OnLoadMoreListener mOnLoadMoreListener;

        public OnLoadMoreListenerWrapper(OnLoadMoreListener onLoadMoreListener) {
            mOnLoadMoreListener = onLoadMoreListener;
        }

        @Override
        public void onLoadMore() {
            if (!mIsGetDataForNet && !isLoadMoreEnable()) {
                mIsGetDataForNet = true;
                mOnLoadMoreListener.onLoadMore();
            }
        }
    }

    public class LoadMoreAdapter extends Adapter<ViewHolder> {

        public static final int LOAD_MORE_VIEW_TYPE = -404;
        public static final int EMPTY_VIEW_TYPE = -403;

        public Adapter mAdapter;

        public LoadMoreAdapter(Adapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == LOAD_MORE_VIEW_TYPE) {
                return new LoadMoreVH();
            } else if (viewType == EMPTY_VIEW_TYPE) {
                return new EmptyVH();
            }
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            super.registerAdapterDataObserver(observer);
            mAdapter.registerAdapterDataObserver(observer);

        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            super.unregisterAdapterDataObserver(observer);
            mAdapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!isFootView(position)) {
                mAdapter.onBindViewHolder(holder, position);
            } else {
                if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                    if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                        ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
                    }
                } else if (getLayoutManager() instanceof GridLayoutManager) {
                    final GridLayoutManager layoutManager = (GridLayoutManager) getLayoutManager();
                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            int viewType = getAdapter().getItemViewType(position);
                            if (viewType < 0) {
                                return layoutManager.getSpanCount();
                            }
                            return 1;
                        }
                    });
                }
                if (holder instanceof VH) {
                    ((VH) holder).onBindViewHolder();
                }
            }
        }

        private boolean isFootView(int position) {
            return position == getItemCount() - 1 && mState != STATE_NONE;
        }

        @Override
        public int getItemViewType(int position) {
            if (!isFootView(position)) {
                return mAdapter.getItemViewType(position);
            } else {
                if (mState == STATE_EMPTY && getItemCount() == 1) {
                    return EMPTY_VIEW_TYPE;
                } else {
                    return LOAD_MORE_VIEW_TYPE;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (mState == STATE_NONE) {
                return mAdapter.getItemCount();
            } else {
                return mAdapter.getItemCount() + 1;
            }
        }

        /**
         * 加载更多的ViewHolder
         */
        private class LoadMoreVH extends VH {

            private View mItemView;

            public LoadMoreVH() {
                super(mFootItem.onCreateView(RecyclerViewWithFooter.this));
                mItemView = itemView;
            }

            @Override
            public void onBindViewHolder() {
                super.onBindViewHolder();
                if (mState == STATE_LOADING || mState == STATE_END || mState == STATE_PULL_TO_LOAD) {
                    mFootItem.onBindData(mItemView, mState);
                }
            }
        }

        /**
         * 数据为空时的ViewHolder
         */
        private class EmptyVH extends VH {

            public EmptyVH() {
                super(mEmptyItem.onCreateView(RecyclerViewWithFooter.this));
            }

            @Override
            public void onBindViewHolder() {
                super.onBindViewHolder();
                mEmptyItem.onBindData(itemView);
            }
        }

        class VH extends ViewHolder {

            public VH(View itemView) {
                super(itemView);
            }

            public void onBindViewHolder() {

            }
        }
    }
}
