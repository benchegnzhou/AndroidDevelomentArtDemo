package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable
import kotlin.math.min

/**
 * 参考：https://blog.csdn.net/u010126792/article/details/85253038
 */
class ComposeShadeDstView(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr), View.OnClickListener {

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    init {
        setOnClickListener(this)
    }

    val mPaint by lazy {
        val paint = Paint()
        paint.color = Color.parseColor("#FFE91E63")
        paint.isAntiAlias = true
        paint
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (!isAttachToWindows) {
            return
        }

        canvas?.apply {
            save()
            drawCircle(
                width / 5 * 3f,
                height / 2f,
                min(width, height) / 3f,
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

            var title = "后面使用Dst标识哈！"

            val fontMetrics: Paint.FontMetrics = paint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline: Float = height.toFloat() / 2 + distance
            drawText(title, width / 2f, baseline + height / 4 + 50f, paint)
            drawText("这个是目标图片", width / 2f, baseline + height / 4, paint)
        }
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

    override fun onClick(p0: View?) {

    }
}