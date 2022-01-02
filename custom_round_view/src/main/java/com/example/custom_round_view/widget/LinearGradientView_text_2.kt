package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable

/**
 * 参考：https://blog.csdn.net/qq_30889373/article/details/78793086
 */
class LinearGradientView_text_2(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr), View.OnClickListener {

    var right = true
    var bottom = true
    var current_ballX = 0f
    var current_ballY = 0f

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
        paint.textSize = 78f
        paint.textAlign = Paint.Align.CENTER
        paint.typeface = Typeface.DEFAULT_BOLD
        paint
    }
    var array = arrayListOf(0.15f, 0.3f, 0.6f, 1.0f).toFloatArray()
    private val RECT by lazy { RectF(0f, 0f, width * 0.2f, height * 0.2f) }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        val linearGradient =
            LinearGradient(
                RECT.left,
                RECT.top,
                RECT.right,
                RECT.bottom,
                arrayListOf(
                    Color.parseColor("#FFF0F0F0"),
                    Color.parseColor("#FFB5B5B5"),
                    Color.parseColor("#FFCD0000"),
                    Color.parseColor("#FF9400D3")
                ).toIntArray(),
                array,
                Shader.TileMode.MIRROR
            )

        linearGradient.setLocalMatrix(Matrix().apply { setTranslate(current_ballX, current_ballY) })
        current_ballX += 7
        current_ballY += 7
        mPaint.shader = linearGradient

        canvas?.apply {
            save()
            val fontMetrics: Paint.FontMetrics = mPaint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline: Float = height.toFloat() / 2 + distance
            canvas?.drawText("测试文字。。。", width / 2.0f, baseline, mPaint)
            restore()
        }
        if (isAttachToWindows) {
            postInvalidateDelayed(2)
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

            var title = "当前的颜色比为："


            title =
                "$title ${array[0]} : ${array[1] - array[0]} : ${array[2] - array[1]}: ${array[3] - array[2]}"

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
        val random3 = getRandom()
        array = arrayListOf(
            random1,
            random2 + random1,
            random3 + random2 + random1,
            1.0f
        ).toFloatArray()
        invalidate()
    }

    private fun getRandom(): Float {
        return (1..3).random() / 10.0f
    }

    var isAttachToWindows = false;
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