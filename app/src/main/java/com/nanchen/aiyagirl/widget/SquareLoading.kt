package com.nanchen.aiyagirl.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.nanchen.aiyagirl.R
import java.util.*

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  16:48
 */

class SquareLoading : ViewGroup {


    private val startAnims = ArrayList<RotateAnimation>()
    private val reverseAnims = ArrayList<RotateAnimation>()

    private var mContext: Context? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {}

    private fun init(context: Context, attrs: AttributeSet?) {
        removeViewsIfNeeded()
        mContext = context
        if (attrs != null) {
            val a = getContext().obtainStyledAttributes(attrs, R.styleable.SquareLoading)

            mSquareColor = a.getColor(R.styleable.SquareLoading_squareColor, DEFAULT_SQUARE_COLOR)
            mSquareSize = a.getDimensionPixelSize(R.styleable.SquareLoading_squareSize, DEFAULT_SQUARE_SIZE)
            mSquareCorner = a.getDimensionPixelSize(R.styleable.SquareLoading_squareCorner, DEFAULT_SQUARE_CORNER)
            mDividerSize = a.getDimensionPixelSize(R.styleable.SquareLoading_dividerSize, DEFAULT_DIVIDER_SIZE)

            val xCount = a.getInteger(R.styleable.SquareLoading_xCount, DEFUALT_X_COUNT)
            val yCount = a.getInteger(R.styleable.SquareLoading_yCount, DEFUALT_Y_COUNT)
            if (xCount in 2..6) {
                mXCount = xCount
            }
            if (yCount in 2..6) {
                mYCount = yCount
            }
            a.recycle()

            mFirstIndex = mXCount * (mYCount - 1)
            mLastIndex = mXCount - 1
        }

        //        initSquare(context);
        //        initAnim();
    }

    fun setSquareColor(color: Int) {
        mSquareColor = color
        initSquare(mContext)
        initAnim()
    }

    private fun removeViewsIfNeeded() {
        if (childCount > 0) {
            removeAllViews()
        }
    }

    private fun initSquare(context: Context?) {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(mSquareColor)
        gradientDrawable.setSize(mSquareSize, mSquareSize)
        gradientDrawable.cornerRadius = mSquareCorner.toFloat()
        for (i in 0 until mXCount * mYCount) {
            val image = ImageView(context)
            image.setImageDrawable(gradientDrawable)
            addView(image)
        }
    }

    private fun initAnim() {
        for (i in 0 until childCount) {
            val startAnim = RotateAnimation(0f, -90f, 0f, mSquareSize.toFloat())
            startAnim.duration = 300
            startAnim.fillAfter = true
            startAnim.interpolator = DecelerateInterpolator()
            startAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    if (i != mLastIndex) {
                        val index = getNextAnimChild(true, i)
                        val delayMillis = if (index > mFirstIndex) 100 else 50
                        startRotateAnim(index, delayMillis)
                    }
                }

                override fun onAnimationEnd(animation: Animation) {
                    if (i == mLastIndex) {
                        startReverseAnim(mLastIndex, 300)
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            startAnims.add(startAnim)

            val reverseAnim = RotateAnimation(-90f, 0f, 0f, mSquareSize.toFloat())
            reverseAnim.duration = 300
            reverseAnim.fillAfter = true
            reverseAnim.interpolator = DecelerateInterpolator()
            reverseAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    if (i != mFirstIndex) {
                        val index = getNextAnimChild(false, i)
                        val delayMillis = if (index < mXCount) 100 else 50
                        startReverseAnim(index, delayMillis)
                    }
                }

                override fun onAnimationEnd(animation: Animation) {
                    if (i == mFirstIndex) {
                        startRotateAnim(mFirstIndex, 300)
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            reverseAnims.add(reverseAnim)
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (childCount > 0) {
            startRotateAnim(mFirstIndex)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        var sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        var sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        val minWidth = mSquareSize * (mXCount + 1) + (mXCount - 1) * mDividerSize
        val minHeight = mSquareSize * (mYCount + 1) + (mYCount - 1) * mDividerSize

        if (widthMode == View.MeasureSpec.AT_MOST || widthMode == View.MeasureSpec.EXACTLY && sizeWidth < minWidth) {
            sizeWidth = minWidth
        }
        if (heightMode == View.MeasureSpec.AT_MOST || heightMode == View.MeasureSpec.EXACTLY && sizeHeight < minHeight) {
            sizeHeight = minHeight
        }

        if (sizeHeight > minHeight) {
            mPaddingTop = (sizeHeight - minHeight) / 2
        }
        if (sizeWidth > minWidth) {
            mPaddingLeft = (sizeWidth - minWidth) / 2
        }

        childLayout()
        setMeasuredDimension(sizeWidth, sizeHeight)
    }

    private fun childLayout() {
        var l: Int
        var t: Int
        var r: Int
        var b: Int
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            l = (i % mXCount + 1) * mSquareSize + i % mXCount * mDividerSize + mPaddingLeft
            t = (i / mXCount + 1) * mSquareSize + i / mXCount * mDividerSize + mPaddingTop
            r = l + mSquareSize
            b = t + mSquareSize
            child.layout(l, t, r, b)
        }
    }


    private fun startRotateAnim(index: Int) {
        if (startAnims.size > index) {
            getChildAt(index).startAnimation(startAnims[index])
        }
    }

    private fun startReverseAnim(index: Int) {
        if (reverseAnims.size > index) {
            getChildAt(index).startAnimation(reverseAnims[index])
        }
    }

    private fun startRotateAnim(index: Int, delayMillis: Int) {
        postDelayed({ startRotateAnim(index) }, delayMillis.toLong())
    }

    private fun startReverseAnim(index: Int, delayMillis: Int) {
        postDelayed({ startReverseAnim(index) }, delayMillis.toLong())
    }

    private fun getNextAnimChild(isStart: Boolean, i: Int): Int {
        var i = i
        val index: Int
        if (isStart) {
            if (i < mXCount) {
                i += mFirstIndex + 1
                return i
            }
            index = i - mXCount
        } else {
            if (i > mFirstIndex) {
                i -= mFirstIndex + 1
                return i
            }
            index = i + mXCount
        }
        return index

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAnims(startAnims)
        cancelAnims(reverseAnims)
    }

    private fun cancelAnims(anims: MutableList<RotateAnimation>?) {
        if (anims != null) {
            for (anim in anims) {
                anim.cancel()
            }
            anims.clear()
        }
    }

    companion object {

        private const val DEFAULT_SQUARE_COLOR = Color.WHITE
        private const val DEFAULT_SQUARE_SIZE = 36
        private const val DEFAULT_SQUARE_CORNER = 8
        private const val DEFAULT_DIVIDER_SIZE = 8
        private const val DEFUALT_X_COUNT = 4
        private const val DEFUALT_Y_COUNT = 3
        private const val DEFAULT_FIRST_INDEX = DEFUALT_X_COUNT * (DEFUALT_Y_COUNT - 1)
        private const val DEFAULT_LAST_INDEX = DEFUALT_X_COUNT - 1


        private var mSquareColor = DEFAULT_SQUARE_COLOR
        private var mSquareSize = DEFAULT_SQUARE_SIZE
        private var mSquareCorner = DEFAULT_SQUARE_CORNER
        private var mDividerSize = DEFAULT_DIVIDER_SIZE
        private var mXCount = DEFUALT_X_COUNT
        private var mYCount = DEFUALT_Y_COUNT
        private var mFirstIndex = DEFAULT_FIRST_INDEX
        private var mLastIndex = DEFAULT_LAST_INDEX

        private var mPaddingTop = 0
        private var mPaddingLeft = 0
    }
}
