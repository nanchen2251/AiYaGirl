package com.nanchen.aiyagirl.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.nanchen.aiyagirl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  16:48
 */

public class SquareLoading extends ViewGroup {

    private static final int DEFAULT_SQUARE_COLOR = Color.WHITE;
    private static final int DEFAULT_SQUARE_SIZE = 36;
    private static final int DEFAULT_SQUARE_CORNER = 8;
    private static final int DEFAULT_DIVIDER_SIZE = 8;
    private static final int DEFUALT_X_COUNT = 4;
    private static final int DEFUALT_Y_COUNT = 3;
    private static final int DEFAULT_FIRST_INDEX = DEFUALT_X_COUNT * (DEFUALT_Y_COUNT - 1);
    private static final int DEFAULT_LAST_INDEX = DEFUALT_X_COUNT - 1;


    private static int mSquareColor = DEFAULT_SQUARE_COLOR;
    private static int mSquareSize = DEFAULT_SQUARE_SIZE;
    private static int mSquareCorner = DEFAULT_SQUARE_CORNER;
    private static int mDividerSize = DEFAULT_DIVIDER_SIZE;
    private static int mXCount = DEFUALT_X_COUNT;
    private static int mYCount = DEFUALT_Y_COUNT;
    private static int mFirstIndex = DEFAULT_FIRST_INDEX;
    private static int mLastIndex = DEFAULT_LAST_INDEX;

    private static int mPaddingTop = 0;
    private static int mPaddingLeft = 0;


    private List<RotateAnimation> startAnims = new ArrayList<>();
    private List<RotateAnimation> reverseAnims = new ArrayList<>();

    private Context mContext;

    public SquareLoading(Context context) {
        super(context);
        init(context, null);
    }

    public SquareLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SquareLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    private void init(Context context, AttributeSet attrs) {
        removeViewsIfNeeded();
        mContext = context;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SquareLoading);

            mSquareColor = a.getColor(R.styleable.SquareLoading_squareColor, DEFAULT_SQUARE_COLOR);
            mSquareSize = a.getDimensionPixelSize(R.styleable.SquareLoading_squareSize, DEFAULT_SQUARE_SIZE);
            mSquareCorner = a.getDimensionPixelSize(R.styleable.SquareLoading_squareCorner, DEFAULT_SQUARE_CORNER);
            mDividerSize = a.getDimensionPixelSize(R.styleable.SquareLoading_dividerSize, DEFAULT_DIVIDER_SIZE);

            int xCount = a.getInteger(R.styleable.SquareLoading_xCount, DEFUALT_X_COUNT);
            int yCount = a.getInteger(R.styleable.SquareLoading_yCount, DEFUALT_Y_COUNT);
            if (xCount >= 2 && xCount <= 6) {
                mXCount = xCount;
            }
            if (yCount >= 2 && yCount <= 6) {
                mYCount = yCount;
            }
            a.recycle();

            mFirstIndex = mXCount * (mYCount - 1);
            mLastIndex = mXCount - 1;
        }

//        initSquare(context);
//        initAnim();
    }

    public void setSquareColor(int color) {
        mSquareColor = color;
        initSquare(mContext);
        initAnim();
    }

    private void removeViewsIfNeeded() {
        if (getChildCount() > 0) {
            removeAllViews();
        }
    }

    private void initSquare(Context context) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(mSquareColor);
        gradientDrawable.setSize(mSquareSize, mSquareSize);
        gradientDrawable.setCornerRadius(mSquareCorner);
        for (int i = 0; i < mXCount * mYCount; i++) {
            ImageView image = new ImageView(context);
            image.setImageDrawable(gradientDrawable);
            addView(image);
        }
    }

    private void initAnim() {
        for (int i = 0; i < getChildCount(); i++) {
            RotateAnimation startAnim = new RotateAnimation(0, -90, 0, mSquareSize);
            startAnim.setDuration(300);
            startAnim.setFillAfter(true);
            startAnim.setInterpolator(new DecelerateInterpolator());
            final int finalI = i;
            startAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (finalI != mLastIndex) {
                        int index = getNextAnimChild(true, finalI);
                        int delayMillis = index > mFirstIndex ? 100 : 50;
                        startRotateAnim(index, delayMillis);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (finalI == mLastIndex) {
                        startReverseAnim(mLastIndex, 300);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            startAnims.add(startAnim);

            RotateAnimation reverseAnim = new RotateAnimation(-90, 0, 0, mSquareSize);
            reverseAnim.setDuration(300);
            reverseAnim.setFillAfter(true);
            reverseAnim.setInterpolator(new DecelerateInterpolator());
            final int finalI1 = i;
            reverseAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (finalI1 != mFirstIndex) {
                        int index = getNextAnimChild(false, finalI1);
                        int delayMillis = index < mXCount ? 100 : 50;
                        startReverseAnim(index, delayMillis);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (finalI1 == mFirstIndex) {
                        startRotateAnim(mFirstIndex, 300);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            reverseAnims.add(reverseAnim);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getChildCount() > 0) {
            startRotateAnim(mFirstIndex);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int minWidth = mSquareSize * (mXCount + 1) + (mXCount - 1) * mDividerSize;
        int minHeight = mSquareSize * (mYCount + 1) + (mYCount - 1) * mDividerSize;

        if (widthMode == MeasureSpec.AT_MOST || (widthMode == MeasureSpec.EXACTLY && sizeWidth < minWidth)) {
            sizeWidth = minWidth;
        }
        if (heightMode == MeasureSpec.AT_MOST || (heightMode == MeasureSpec.EXACTLY && sizeHeight < minHeight)) {
            sizeHeight = minHeight;
        }

        if (sizeHeight > minHeight) {
            mPaddingTop = (sizeHeight - minHeight) / 2;
        }
        if (sizeWidth > minWidth) {
            mPaddingLeft = (sizeWidth - minWidth) / 2;
        }

        childLayout();
        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    private void childLayout() {
        int l, t, r, b;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            l = (i % mXCount + 1) * mSquareSize + (i % mXCount) * mDividerSize + mPaddingLeft;
            t = (i / mXCount + 1) * mSquareSize + (i / mXCount) * mDividerSize + mPaddingTop;
            r = l + mSquareSize;
            b = t + mSquareSize;
            child.layout(l, t, r, b);
        }
    }


    private void startRotateAnim(final int index) {
        if (startAnims != null && startAnims.size() > index) {
            getChildAt(index).startAnimation(startAnims.get(index));
        }
    }

    private void startReverseAnim(final int index) {
        if (reverseAnims != null && reverseAnims.size() > index) {
            getChildAt(index).startAnimation(reverseAnims.get(index));
        }
    }

    private void startRotateAnim(final int index, int delayMillis) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startRotateAnim(index);
            }
        }, delayMillis);
    }

    private void startReverseAnim(final int index, int delayMillis) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startReverseAnim(index);
            }
        }, delayMillis);
    }

    private int getNextAnimChild(boolean isStart, int i) {
        int index;
        if (isStart) {
            if (i < mXCount) {
                i += mFirstIndex + 1;
                return i;
            }
            index = i - mXCount;
        } else {
            if (i > mFirstIndex) {
                i -= mFirstIndex + 1;
                return i;
            }
            index = i + mXCount;
        }
        return index;

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnims(startAnims);
        cancelAnims(reverseAnims);
    }

    private void cancelAnims(List<RotateAnimation> anims) {
        if (anims != null) {
            for (RotateAnimation anim : anims) {
                anim.cancel();
            }
            anims.clear();
        }
    }
}
