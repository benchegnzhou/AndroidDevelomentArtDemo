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
class ComposeShadePorterModeView_DST_ATOP(
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
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)
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

            val title = "地方绘制源图像，并且相交处的效果会受到源图像和目标图像alpha的影响"
            val fontMetrics: Paint.FontMetrics = paint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline: Float = height.toFloat() / 2 + distance
            drawText(title, width / 2f, baseline + height / 4 + 50f, paint)
            drawText("DST_ATOP：源图像和目标图像相交处绘制目标图像，不相交的", width / 2f, baseline + height / 4, paint)
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