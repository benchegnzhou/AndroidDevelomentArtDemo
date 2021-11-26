package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.example.custom_round_view.R

/**
 * 文章参考 https://www.jianshu.com/p/db401403385b
 */
class ProterDuffModeRoundImageView(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr) {


    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)

    val mPaint by lazy {
        val mPaint: Paint = Paint()
        mPaint.color = 0x00ff0055
        mPaint
    }

    //https://www.jianshu.com/p/d11892bbe055
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val srcBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val destBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        createBitmap.eraseColor(0)
        mPaint.color = Color.parseColor("#ffE64A19")
//        mPaint.isAntiAlias = true                       //设置画笔为无锯齿
        mPaint.setStrokeWidth(15f);//设置画笔粗细
        mPaint.style = Paint.Style.FILL                 //实心矩形框

        Canvas(srcBitmap).drawRoundRect(
            RectF(
                50f,
                50f,
                srcBitmap.width.toFloat(),
                srcBitmap.height.toFloat()
            ), 30f, 30f, mPaint
        )

        mPaint.color = Color.parseColor("#ff1E88E5")
        Canvas(destBitmap).drawRect(
            0f,
            0f,
            destBitmap.width.toFloat(),
            destBitmap.height.toFloat(),
            mPaint
        )



        canvas?.apply {
//            save()
            //背景色设为白色，方便比较效果
            canvas.drawColor(Color.WHITE)
            var newPaint = Paint()
            canvas.drawBitmap(destBitmap, null, Rect(0, 0, width, height), newPaint)
            newPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)  //设置图层混合模式
            canvas.drawBitmap(srcBitmap, null, Rect(0, 0, width, height), newPaint)
            newPaint.xfermode = null   // 用完及时清除 Xfermode
//            restore()
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }


}