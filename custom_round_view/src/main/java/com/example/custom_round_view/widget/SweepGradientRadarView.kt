package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import kotlin.math.max
import kotlin.math.min

/**
 * 参考：https://blog.csdn.net/lylodyf/article/details/70053205?spm=1001.2101.3001.6650.3&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-3.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-3.no_search_link&utm_relevant_index=4
 */
class SweepGradientRadarView(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr), View.OnClickListener {

    private var mCurrentRotate = 0.0f

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


    //    var array = arrayListOf(
//        0.2f,
//        0.4f,
//        0.6f,
//        0.8f,
//        1.0f
//    ).toFloatArray()
    private val RECT by lazy { RectF(0f, 0f, width * 0.2f, height * 0.2f) }
    private var mMatrix = Matrix()
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
                    Color.parseColor("#0083B600"),
                    Color.parseColor("#4583B600"),
                    Color.parseColor("#8683B600"),
                    Color.parseColor("#AC83B600"),
                    Color.parseColor("#FF83B600")
                ).toIntArray(),
                null
            )
        mPaint.shader = linearGradient
        val min = min(width.toFloat(), height.toFloat()) / 2.0f
        canvas?.apply {
            save()
            drawCircle(width / 2.0f,
                height / 2.0f,
                min, Paint().apply {
                    color = Color.parseColor("#FFF5EAFA")
                    strokeWidth = 2f
                    style = Paint.Style.FILL_AND_STROKE
                    isAntiAlias = true
                })

            concat(mMatrix)
            drawCircle(
                width / 2.0f,
                height / 2.0f,
                min(width, height) / 2.0f,
                mPaint
            )
            restore()
            //绘制雷达线
            drawRadarCircle(this, min / 4)
            drawRadarCircle(this, min / 2)
            drawRadarCircle(this, min / 4 * 3)
            drawRadarCircle(this, min)
            drawRadarLine(canvas, width / 2.0f, 0f, width / 2.0f, height.toFloat())
            drawRadarLine(canvas, 0f, height / 2.0f, width.toFloat(), height / 2.0f)
        }
        drawText(canvas)

        mCurrentRotate += 1f
        postInvalidateDelayed(1)
        mMatrix.reset()
        mMatrix.postRotate(mCurrentRotate, width / 2.0f, height / 2.0f)
    }

    private fun drawRadarLine(
        canvas: Canvas?,
        startX: Float,
        startY: Float,
        stopX: Float,
        stopY: Float
    ) {
        canvas?.apply {
            drawLine(startX, startY, stopX, stopY, Paint().apply {
                color = Color.parseColor("#FFFFFFFF")
                strokeWidth = 1f
                style = Paint.Style.STROKE
                isAntiAlias = true
            })
        }
    }

    private fun drawRadarCircle(canvas: Canvas?, radius: Float) {
        canvas?.apply {
            drawCircle(width / 2.0f,
                height / 2.0f,
                radius,
                Paint().apply {
                    color = Color.parseColor("#FFFFFFFF")
                    strokeWidth = 1f
                    style = Paint.Style.STROKE
                    isAntiAlias = true
                })
        }
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

            var title = "当前的颜色比为：0083B600 -> FF83B600"


//            title = "$title ${array[0]} : ${array[1] - array[0]} : ${array[2] - array[1]}"

            val fontMetrics: Paint.FontMetrics = paint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline: Float = height.toFloat() / 2 + distance
            canvas?.drawText(title, width / 2f, baseline + height / 4 + 50f, paint)
            canvas?.drawText("雷达效果", width / 2f, baseline + height / 4, paint)
        }
    }

    override fun onClick(v: View?) {
//        val random1 = getRandom()
//        val random2 = getRandom()
//        array = arrayListOf(random1, random2 + random1, 1.0f).toFloatArray()
//        invalidate()
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