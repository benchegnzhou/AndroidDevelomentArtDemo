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
class LinearGradientView_1(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr) {

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    init {

    }

    private var mMode: Path.FillType = Path.FillType.WINDING

    val mPaint by lazy {
        val paint = Paint()
        paint.color = Color.parseColor("#AB47BC")
        paint
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val linearGradient =
            LinearGradient(200f, 200f, 700f, 600f, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
        mPaint.shader = linearGradient

        canvas?.apply {
            save()
            val path = Path()
            path.fillType = mMode
            path.addRoundRect(
                RectF(mStartX, mStartY, mWidth + mStartX, mHeight + mStartY),
                50f,
                50f,
                Path.Direction.CW
            )
            canvas.drawPath(path, mPaint)
            drawTitle(canvas)
            restore()
        }
    }


    public fun changePathFillMode(){
        when (mMode) {
            Path.FillType.WINDING -> {
                mMode = Path.FillType.EVEN_ODD
            }
            Path.FillType.EVEN_ODD -> {
                mMode = Path.FillType.INVERSE_WINDING
            }
            Path.FillType.INVERSE_WINDING -> {
                mMode = Path.FillType.INVERSE_EVEN_ODD
            }
            Path.FillType.INVERSE_EVEN_ODD -> {
                mMode = Path.FillType.WINDING
            }
        }
        invalidate()
    }
    private fun drawTitle(canvas: Canvas) {
        val paint = Paint()
        paint.apply {
            color = Color.BLACK
            textSize = 46f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        var title = ""
        when (mMode) {
            Path.FillType.WINDING -> {
                title = "当前path.fillType = Path.FillType.WINDING"
            }
            Path.FillType.EVEN_ODD -> {
                title = "当前path.fillType = Path.FillType.EVEN_ODD"
            }
            Path.FillType.INVERSE_WINDING -> {
                title = "当前path.fillType = Path.FillType.INVERSE_WINDING"
            }
            Path.FillType.INVERSE_EVEN_ODD -> {
                title = "当前path.fillType = Path.FillType.INVERSE_EVEN_ODD"
            }
        }
        val fontMetrics: Paint.FontMetrics = paint.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseline: Float = height.toFloat() / 2 + distance
        canvas?.drawText(title, width / 2f, baseline + height / 4 + 50f, paint)
        canvas?.drawText("点击可拖动", width / 2f, baseline + height / 4 , paint)

    }

    private var mWidth = 300f
    private var mHeight = 200f
    private var mStartX = 0f
    private var mStartY = 0f
    private var tempX = 0f
    private var tempY = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                this@LinearGradientView_1.parent.requestDisallowInterceptTouchEvent(true)
                tempX = event.x
                tempY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                calcXY(event, false)
                tempX = event.x
                tempY = event.y
            }
            MotionEvent.ACTION_UP -> {
                calcXY(event, true)
                this@LinearGradientView_1.parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return true
    }

    private fun calcXY(event: MotionEvent?, isUp: Boolean) {
        event?.apply {
            var difX = x - tempX
            mStartX += difX;
            if (mStartX < 0) {
                mStartX = 0f
            }
            if (mStartX + mWidth > width) {
                mStartX = width - mWidth
            }

            var difY = y - tempY
            mStartY += difY
            if (mStartY < 0) {
                mStartY = 0f
            }
            if (mStartY + mHeight > height) {
                mStartY = height - mHeight
            }
            invalidate()
        }
    }

}