package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import kotlin.math.max
import kotlin.math.min

/**
 * 参考：https://blog.csdn.net/u010126792/article/details/85238050
 */
class SweepGradientView_4(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr), View.OnClickListener {
    private var currentAngle = 0f
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


    var array = arrayListOf(
        0.2f,
        0.4f,
        0.6f,
        0.8f,
        1.0f
    ).toFloatArray()
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
                    Color.parseColor("#FF83B600"),
                    Color.parseColor("#FF83B600"),
                    Color.parseColor("#FFFFC641"),
                    Color.parseColor("#FFFF8A00"),
                    Color.parseColor("#FFCC0000")
                ).toIntArray(),
                array
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
            val left = (width - min(width.toFloat(), height.toFloat())) / 2.0f
            val top = (height - min(width.toFloat(), height.toFloat())) / 2.0f
            drawArc(
                RectF(
                    left,
                    top,
                    width.toFloat() - left,
                    height.toFloat() - top
                ), 0f,
                currentAngle,
                true,
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

        currentAngle += if (isClockwise) 2f else -2f

        if (currentAngle < 0f) {
            currentAngle = 0f
            isClockwise = true
        } else if (currentAngle > 360f) {
            currentAngle = 360f
            isClockwise = false
        }
        postInvalidateDelayed(2)
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
//        val random1 = getRandom()
//        val random2 = getRandom()
//        array = arrayListOf(random1, random2 + random1, 1.0f).toFloatArray()
//        invalidate()
    }

    private fun update() {
        val offset = currentAngle / 360.0f
//        array = arrayListOf(
//            color1 + offset,
//            color1 + color2 + offset,
//            color1 + color2 + color3 + offset,
//            color1 + color2 + color3 + color4 + offset,
//            color1 + color2 + color3 + color4 + color5 + offset,
//            color1 + color2 + color3 + color4 + color5 +color6 + offset,
//            1.0f
//        ).toFloatArray()
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