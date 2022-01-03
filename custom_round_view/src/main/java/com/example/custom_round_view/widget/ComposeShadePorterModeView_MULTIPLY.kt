package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import kotlin.math.min

/**
 * 参考：https://blog.csdn.net/u010126792/article/details/85253038
 */
class ComposeShadePorterModeView_MULTIPLY(
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
        paint.color = Color.parseColor("#FF2196F3")
        paint.isAntiAlias = true
        paint
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (!isAttachToWindows) {
            return
        }

        canvas?.apply {
            drawColor(Color.WHITE)
            val rectF = RectF(0F, 0f, width.toFloat(), height.toFloat())
            saveLayer(rectF, mPaint)
            drawBitmap(mDestBitmap, 0f, 0f, mPaint)
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
            drawBitmap(mSrcBitmap, 0f, 0f, mPaint)
            mPaint.xfermode = null
            restore()
        }
        drawText(canvas)
    }


    val mSrcBitmap by lazy {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        mPaint.color = Color.parseColor("#FF2196F3")
        canvas.drawRect(
            RectF(
                140f,
                height / 5f,
                width / 7f * 4,
                height / 5f * 4,
            ),
            mPaint
        )
        bitmap
    }
    val mDestBitmap by lazy {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        mPaint.color = Color.parseColor("#FFE91E63")
        canvas.drawCircle(
            width / 5 * 3f,
            height / 2f,
            min(width, height) / 3f,
            mPaint
        )
        bitmap
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

            val title =
                "结果色总是较暗的颜色，任何颜色与黑色复合产生黑色，任何颜色与白色复合保持不变，当用黑色或白色以外的颜色绘画时，绘画工具绘制的连续描边产生逐渐变暗的颜色。"
            val fontMetrics: Paint.FontMetrics = paint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline: Float = height.toFloat() / 2 + distance
            drawText(title, width / 2f, baseline + height / 4 + 50f, paint)
            drawText(
                "MULTIPLY：正片叠底，源图像素颜色值乘以目标图像素颜色值除以255得到混合后图像像素颜色值。",
                width / 2f,
                baseline + height / 4,
                paint
            )
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