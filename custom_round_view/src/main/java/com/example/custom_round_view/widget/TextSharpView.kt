package com.example.custom_round_view.widget


import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.example.custom_round_view.R

/**
 * 使用shader绘制TextView
 * 自定义属性参考：https://www.jianshu.com/p/8844de6addb3
 *
 */
class TextSharpView(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) : View(context, attrs, defStyleAttr) {

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    private var mText: String = ""
    private var mTextColor: Int = Color.BLACK
    private var mTextBackground: Int = Color.BLACK
    private var mTextSize: Float = 64f

    init {
        val ta: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.TextSharpView)
        mText = ta.getString(R.styleable.TextSharpView_android_text) ?: ""
        mTextColor = ta.getColor(R.styleable.TextSharpView_mTestColor, Color.BLACK)
        mTextBackground = ta.getColor(R.styleable.TextSharpView_mTestBackground, Color.parseColor("#F9F9F9"))
        mTextSize = ta.getDimension(R.styleable.TextSharpView_mTestSize, 100f)
        ta.recycle() //注意回收
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
        canvas?.drawColor(mTextBackground)

        val shader = LinearGradient(
            0f, 0f, width.toFloat(), height.toFloat(),
            mTextColor, mTextColor, Shader.TileMode.CLAMP
        )

        mPaint.shader = shader
        mPaint.textSize = mTextSize

        // 文字居中参考：https://www.jianshu.com/p/8b97627b21c4
        //计算baseline
        val fontMetrics: Paint.FontMetrics = mPaint.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseline: Float = height.toFloat()/2 + distance
        canvas?.drawText(mText, width/2f, baseline , mPaint)


    }


}