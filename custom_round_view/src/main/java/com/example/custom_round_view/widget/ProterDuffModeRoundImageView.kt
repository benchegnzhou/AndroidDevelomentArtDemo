package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import kotlinx.coroutines.*

/**
 * 文章参考 https://www.jianshu.com/p/db401403385b
 * 实际绘制发现，需要关闭硬件加速，不然会出现黑色
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

    var drawCode = 0

    init {
        //禁止硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                val result = withContext(Dispatchers.IO) {
                    // 网络请求，并return请求结果
                    delay(2000)
                }
                // 更新UI
                invalidate()
                drawCode++
                if (drawCode == 3) {
                    drawCode = 0
                }
            }
        }
    }

    constructor(context: Context?) : this(context, null, 0)

    val mPaint by lazy {
        val mPaint: Paint = Paint()
        mPaint.color = 0x00ff0055
        mPaint
    }


    val srcBitmap by lazy {
        mPaint.color = Color.parseColor("#ffE64A19")
        mPaint.isAntiAlias = true                       //设置画笔为无锯齿
        mPaint.setStrokeWidth(15f);//设置画笔粗细
        mPaint.style = Paint.Style.FILL                 //实心矩形框
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        paint.color = Color.parseColor("#ffE64A19")
        Canvas(bitmap).drawRoundRect(
            RectF(
                0f,
                0f,
                bitmap.width.toFloat(),
                bitmap.height.toFloat()
            ), 60f, 60f, paint
        )
        bitmap
    }
    val destBitmap by lazy {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val paint = Paint()

        paint.color = Color.parseColor("#ff1E88E5")
        Canvas(bitmap).drawRect(
            0f,
            0f,
            bitmap.width.toFloat(),
            bitmap.height.toFloat(),
            paint
        )
        bitmap
    }

    //https://www.jianshu.com/p/d11892bbe055
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        when (drawCode) {
            0 -> {
                drawSrcOlny(canvas)
            }
            1 -> {
                drawDestOlny(canvas)
            }
            2 -> {
                drawMixOlny(canvas)
            }
        }
    }


    fun drawSrcOlny(canvas: Canvas?) {

        canvas?.apply {
            save()
            //背景色设为白色，方便比较效果
            canvas.drawColor(Color.WHITE)
            var newPaint = Paint()
            canvas.drawBitmap(srcBitmap, null, Rect(0, 0, width, height), newPaint)
            newPaint.xfermode = null   // 用完及时清除 Xfermode
            newPaint.color = Color.parseColor("#212121")
            newPaint.textSize = 42f
            newPaint.strokeWidth = 3f
            newPaint.style = Paint.Style.STROKE
            newPaint.isAntiAlias = true
            canvas.drawText("绘制src", 60f, (height / 2 + 21f), newPaint)
            restore()
        }
    }

    fun drawDestOlny(canvas: Canvas?) {

        canvas?.apply {
            save()
            //背景色设为白色，方便比较效果
            canvas.drawColor(Color.WHITE)
            var newPaint = Paint()
            canvas.drawBitmap(destBitmap, null, Rect(0, 0, width, height), newPaint)
            newPaint.xfermode = null   // 用完及时清除 Xfermode
            newPaint.color = Color.parseColor("#212121")
            newPaint.textSize = 42f
            newPaint.strokeWidth = 3f
            newPaint.style = Paint.Style.STROKE
            newPaint.isAntiAlias = true
            canvas.drawText("绘制dest", 60f, (height / 2 + 21f), newPaint)
            restore()
        }
    }

    fun drawMixOlny(canvas: Canvas?) {

        canvas?.apply {
            save()
            //背景色设为白色，方便比较效果
            canvas.drawColor(Color.WHITE)

            canvas.drawBitmap(destBitmap, null, Rect(0, 0, width, height), mPaint)
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)  //设置图层混合模式
            canvas.drawBitmap(srcBitmap, null, Rect(0, 0, width, height), mPaint)
            mPaint.xfermode = null   // 用完及时清除 Xfermode
            mPaint.color = Color.parseColor("#212121")
            mPaint.textSize = 42f
            mPaint.strokeWidth = 3f
            mPaint.style = Paint.Style.STROKE
            mPaint.isAntiAlias = true
            canvas.drawText(
                "使用xfermode的形式绘制圆角图形,最终剩余的图形部分是src部分",
                60f,
                (height / 2 + 21f),
                mPaint
            )
            restore()
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }


}