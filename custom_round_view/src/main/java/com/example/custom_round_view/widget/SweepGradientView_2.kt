package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable

/**
 * 参考：https://blog.csdn.net/u010126792/article/details/85238050
 */
class SweepGradientView_2(
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
    var array = arrayListOf(0.1f, 0.2f, 1.0f).toFloatArray()
    private val RECT by lazy { RectF(0f, 0f, width * 0.2f, height * 0.2f) }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (!isAttachToWindows) {
            return
        }
        val linearGradient =
            SweepGradient(
                width / 2.0f,
                height / 2.0f,
                arrayListOf(
                    Color.parseColor("#FFEE3A8C"),
                    Color.parseColor("#FFCD0000"),
                    Color.parseColor("#FF9400D3")
                ).toIntArray(),
                array
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
        postInvalidateDelayed(6)
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

            var title = "当前的颜色比为："


            title = "$title ${array[0]} : ${array[1] - array[0]} : ${array[2] - array[1]}"

            val fontMetrics: Paint.FontMetrics = paint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline: Float = height.toFloat() / 2 + distance
            canvas?.drawText(title, width / 2f, baseline + height / 4 + 50f, paint)
            canvas?.drawText("点击可切换比例", width / 2f, baseline + height / 4, paint)
        }
    }

    override fun onClick(v: View?) {
        val random1 = getRandom()
        val random2 = getRandom()
        array = arrayListOf(random1, random2 + random1, 1.0f).toFloatArray()
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