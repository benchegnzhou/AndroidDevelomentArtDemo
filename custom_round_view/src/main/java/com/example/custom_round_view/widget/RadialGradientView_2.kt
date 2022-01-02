package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable
import kotlin.math.min

/**
 * 参考：https://blog.csdn.net/u010126792/article/details/85245372
 */
class RadialGradientView_2(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr), View.OnClickListener {
    private var currentCorner = 0f
    private var isClockwise = true

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    init {
        setOnClickListener(this)
    }


    val mPaint by lazy {
        val paint = Paint()
        paint.color = Color.BLACK
        paint.isAntiAlias = true
        paint
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (!isAttachToWindows) {
            return
        }
        val linearGradient =
            RadialGradient(
                width / 2.0f,
                height / 2.0f,
                min(width, height) / 2.0f,
                Color.parseColor("#FFFF4444"),
                Color.parseColor("#FF33B5E5"),
                Shader.TileMode.CLAMP
            )
        mPaint.shader = linearGradient

        canvas?.apply {
            save()
            drawRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                mPaint
            )
            restore()
        }
        drawText(canvas)

    }


    private fun drawText(canvas: Canvas?) {
        canvas?.apply {
            val paint = Paint()
            paint.apply {
                color = Color.BLACK
                textSize = 46f
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
            }

            var title = "当前的渐变色为FFFF4444 -> FF33B5E5 -> FFE2F4FB"

            val fontMetrics: Paint.FontMetrics = paint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline: Float = height.toFloat() / 2 + distance
            canvas?.drawText(title, width / 2f, baseline + height / 4 + 50f, paint)
            canvas?.drawText("同心圆变化，可以实现水波纹效果", width / 2f, baseline + height / 4, paint)
        }
    }

    override fun onClick(v: View?) {

    }

    private fun getRandom(): Float {
        return (1..5).random() / 10.0f
    }

    var isAttachToWindows = false
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isAttachToWindows = true
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isAttachToWindows = false
    }
}