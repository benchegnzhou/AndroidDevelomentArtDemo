package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable

/**
 * 参考：https://blog.csdn.net/u010126792/article/details/85238050
 */
class SweepGradientView_1(
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
            SweepGradient(
                width / 2.0f,
                height / 2.0f,
                Color.parseColor("#FF000000"),
                Color.parseColor("#FFFF0000"),
            )
        mPaint.shader = linearGradient

        canvas?.apply {
            save()
            canvas.drawRoundRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                currentCorner,
                currentCorner,
                mPaint
            )
            restore()
        }
        drawText(canvas)
        currentCorner += if (isClockwise) 2f else -2f
        val max = Math.min(width, height).toFloat() / 2
        if (currentCorner < 0f) {
            currentCorner = 0f
            isClockwise = true
        } else if (currentCorner > max) {
            currentCorner = max
            isClockwise = false
        }
        postInvalidateDelayed(1)
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

            var title = "当前的渐变色为FF000000 -> FFFF0000"

            val fontMetrics: Paint.FontMetrics = paint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline: Float = height.toFloat() / 2 + distance
            canvas?.drawText(title, width / 2f, baseline + height / 4 + 50f, paint)
            canvas?.drawText("可以发现是从第二象限到第一象限变化的", width / 2f, baseline + height / 4, paint)
        }
    }

    override fun onClick(v: View?) {
        val random1 = getRandom()
        val random2 = getRandom()
        invalidate()
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