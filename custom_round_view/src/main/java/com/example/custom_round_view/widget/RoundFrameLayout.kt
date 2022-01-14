package com.example.custom_round_view.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.Nullable
import com.example.custom_round_view.R

/**
 * 圆角FrameLayout ,测试使用未进行优化处理请不要拷贝使用谢谢！
 */
class RoundFrameLayout(
    context: Context,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) : FrameLayout(context, attrs, defStyleAttr) {
    private val mPath = Path()
    private val DEFAULT_BACKGROUND_COLOR = Color.WHITE
    private val DEFAULT_RADIUS = 60f
    var mBackGroundColor = DEFAULT_BACKGROUND_COLOR
    var mRadius = DEFAULT_RADIUS

    val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = DEFAULT_BACKGROUND_COLOR
        }
    }

    constructor(
        context: Context,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    constructor(
        context: Context
    ) : this(context, null, 0)

    init {
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundFrameLayout)
        mBackGroundColor =
            ta.getColor(R.styleable.RoundFrameLayout_backgroundColor, DEFAULT_BACKGROUND_COLOR)
        mRadius = ta.getDimension(R.styleable.RoundFrameLayout_radius, DEFAULT_RADIUS)
        //Pay attention to recycling
        ta.recycle()
        setWillNotDraw(false)
    }


    override fun onDraw(canvas: Canvas) {
        mPaint.color = mBackGroundColor
        canvas.drawRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            mRadius,
            mRadius,
            mPaint
        )
        super.onDraw(canvas)
    }

    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(color)
    }

    private fun updateBackground() {
        val originalBackground = getOriginalBackground()
        //        final SmoothContainerDrawable smoothBg = new SmoothContainerDrawable();
//        smoothBg.setChildDrawable(originalBackground);
//        smoothBg.setCornerRadius(getRadius());
//        smoothBg.setStrokeWidth(getStrokeWidth());
//        smoothBg.setStrokeColor(getStrokeColor());
        background = originalBackground
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun setBackground(background: Drawable?) {
        super.setBackground(background)
    }

    private fun drawBackground() {
        post {
            mPath.addRoundRect(
                RectF(
                    0f, 0f, width.toFloat(),
                    height.toFloat()
                ), mRadius, mRadius, Path.Direction.CCW
            )
        }
    }

    private fun getOriginalBackground(): Drawable {
        /*if (bg instanceof SmoothContainerDrawable) {
            return ((SmoothContainerDrawable) bg).getChildDrawable();
        } else {
            return bg;
        }*/return background
    }

    private fun getPath() {
        mPath.addRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            mRadius,
            mRadius,
            Path.Direction.CCW
        )
    }

    override fun dispatchDraw(canvas: Canvas) {
        getPath()
        canvas.save()
        canvas.clipPath(mPath)
        super.dispatchDraw(canvas)
        canvas.restore()
    }
}

