package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.Nullable
import com.example.custom_round_view.R
import kotlin.math.min

/**
 * 参考链接：https://blog.csdn.net/u010126792/article/details/85253038
 */
class BitmapShaderViews(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) : RelativeLayout(context, attrs, defStyleAttr) {


    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    val mBackground = Color.parseColor("#F9F9F9")

    init {
        //禁止硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        //解决viewgroup自己不会draw问题https://blog.csdn.net/sinat_26710701/article/details/71171726
        setWillNotDraw(false)
    }

    val mPaint by lazy {
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 230f
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.strokeWidth = 1f
        //居中绘制
        paint.textAlign = Paint.Align.CENTER
        paint.style = Paint.Style.FILL_AND_STROKE
        paint
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制背景
        canvas?.drawColor(mBackground)

        val shader = BitmapShader(
            bgBitmap,
            Shader.TileMode.REPEAT, Shader.TileMode.REPEAT
        )

        mPaint.shader = shader

        //设置了shader之后，相当与绘制的所有图形除了bitmap其他的都会从shader中提取颜色
        canvas?.drawRoundRect(RectF(120f,60f,350f,320f),30f,30f,mPaint)
        canvas?.drawCircle(600f,200f,160f,mPaint)
    }


    val bgBitmap by lazy {

        val bitmap = BitmapFactory.decodeResource(
            context!!.resources,
            R.drawable.sadeqjedhqwj
        )
        //缩放到指定大小
        Bitmap.createScaledBitmap(bitmap, width, height, false)
    }

}