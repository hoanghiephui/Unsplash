package com.unsplash.photo.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntRange
import androidx.annotation.Size
import androidx.appcompat.widget.AppCompatImageView
import com.unsplash.photo.R
import com.unsplash.photo.Utils.dpToPx

class CoverImageView : AppCompatImageView {

    private var topPaint: Paint? = null
    private var bottomPaint: Paint? = null

    private var width = 1f
    private var height = 0.6f

    private var dynamicSize = true
    private var showShadow = false

    private var textPosition: Int = 0

    val size: FloatArray
        @Size(2)
        get() = floatArrayOf(width, height)

    @IntRange(from = 0)
    private annotation class SizeRule {}

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.initialize(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.initialize(context, attrs, defStyleAttr)
    }

    private fun initialize(c: Context, attrs: AttributeSet, defStyleAttr: Int) {
        val a = c.obtainStyledAttributes(attrs, R.styleable.CoverImageView, defStyleAttr, 0)
        this.dynamicSize = a.getBoolean(R.styleable.CoverImageView_civ_dynamic_size, true)
        this.textPosition = a.getInt(R.styleable.CoverImageView_civ_shadow_position, POSITION_NONE)
        a.recycle()

        this.topPaint = Paint()
        this.bottomPaint = Paint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (width >= 0 && height >= 0) {
            if (!dynamicSize) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            } else {
                val size = getMeasureSize(View.MeasureSpec.getSize(widthMeasureSpec), width, height)
                setMeasuredDimension(size[0], size[1])
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
        setPaintStyle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (showShadow) {
            when (textPosition) {
                POSITION_TOP -> {
                    canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), topPaint!!)
                }
                POSITION_BOTTOM -> {
                    canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), bottomPaint!!)
                }
                POSITION_BOTH -> {
                    canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), topPaint!!)
                    canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), bottomPaint!!)
                }
            }
        }
    }

    fun setSize(@SizeRule w: Int?, @SizeRule h: Int?) {
        if (dynamicSize) {
            width = w?.toFloat() ?: 0f
            height = h?.toFloat() ?: 0f
            requestLayout()
        }
    }

    fun setShowShadow(show: Boolean) {
        this.showShadow = show
        invalidate()
    }

    private fun setPaintStyle() {
        topPaint!!.shader = LinearGradient(
            0f, 0f,
            0f, (dpToPx(128, context).toInt()).toFloat(),
            intArrayOf(
                Color.argb((255 * 0.25).toInt(), 0, 0, 0),
                Color.argb((255 * 0.1).toInt(), 0, 0, 0),
                Color.argb((255 * 0.03).toInt(), 0, 0, 0),
                Color.argb(0, 0, 0, 0)
            ),
            null,
            Shader.TileMode.CLAMP
        )

        bottomPaint!!.shader = LinearGradient(
            0f, measuredHeight.toFloat(),
            0f, (measuredHeight - dpToPx(72, context).toInt()).toFloat(),
            intArrayOf(
                Color.argb((255 * 0.25).toInt(), 0, 0, 0),
                Color.argb((255 * 0.1).toInt(), 0, 0, 0),
                Color.argb((255 * 0.03).toInt(), 0, 0, 0),
                Color.argb(0, 0, 0, 0)
            ), null,
            Shader.TileMode.CLAMP
        )
    }

    companion object {
        private val POSITION_NONE = 0
        private val POSITION_TOP = 1
        private val POSITION_BOTTOM = -1
        private val POSITION_BOTH = 2

        @Size(2)
        fun getMeasureSize(measureWidth: Int, w: Float, h: Float): IntArray {
            return intArrayOf(measureWidth, (measureWidth * h / w).toInt())
        }
    }
}