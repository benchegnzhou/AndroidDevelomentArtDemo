package com.example.custom_round_view.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ScrollView
import androidx.annotation.Nullable
import kotlin.math.max

/**
 * 参考：https://blog.csdn.net/u010126792/article/details/85245372
 */
class RadialGradientView_3(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr), View.OnClickListener {
    private var currentCorner = 0f
    private var isClockwise = true

    var array = arrayListOf(0.2f, 1.0f).toFloatArray()
    var currentRadius = 0f

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    init {
        setOnClickListener(this)
        post {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                (parent.parent as ScrollView).setOnScrollChangeListener { p0, p1, p2, p3, p4 ->
                    isCover()
                }
            }
        }

    }


    val mPaint by lazy {
        val paint = Paint()
        paint.color = Color.BLACK
        paint.isAntiAlias = true
        paint
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (!isAttachToWindows) {
            return
        }
        canvas?.apply {
            drawRect(0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                Paint().apply {
                    color = Color.parseColor("#FFC5EAF8")
                    strokeWidth = 16f
                    isAntiAlias = true
                })
        }
        val linearGradient =
            RadialGradient(
                width / 2.0f,
                height / 2.0f,
                max(currentRadius, 1f),
                arrayListOf(
                    Color.parseColor("#FFFFFFFF"),
                    Color.parseColor("#FF40F903")
                ).toIntArray(),
                array,
                Shader.TileMode.CLAMP
            )
        mPaint.shader = linearGradient

        canvas?.apply {
            save()
            drawCircle(
                width.toFloat() / 2.0f,
                height.toFloat() / 2.0f,
                currentRadius,
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

            var title = "当前的渐变色为FFFFFFFF -> FF40F903 "

            val fontMetrics: Paint.FontMetrics = paint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline: Float = height.toFloat() / 2 + distance
            canvas?.drawText(title, width / 2f, baseline + height / 4 + 50f, paint)
            canvas?.drawText("一个简单的水波纹效果", width / 2f, baseline + height / 4, paint)
        }
    }

    override fun onClick(v: View?) {

    }

    private fun getRandom(): Float {
        return (1..5).random() / 10.0f
    }

    var isAttachToWindows = false
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isAttachToWindows = true
    }

    var anim: ValueAnimator? = null
    var animRunning = false
    private fun startAnim() {
        if (animRunning) {
            return
        }
        anim = ValueAnimator.ofFloat(0f, 1.415f * max(width, height).toFloat())
            .apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 2000
                repeatCount = 65535
                repeatMode = ValueAnimator.REVERSE
                addUpdateListener { valueAnimator ->
                    currentRadius = valueAnimator.animatedValue as Float
                    invalidate()
                }
            }
        anim?.start()
        animRunning = true
    }


    private fun isCover(): Boolean {
        val rect = Rect()
        this.getLocalVisibleRect(rect)
        Log.e("---", "top = ${rect.top} , height = ${rect.height()} bottom = ${rect.bottom}")
        if (!animRunning && (rect.top == 0 || (rect.bottom == height && height != 0)) && isAttachToWindows) {
            startAnim()
        } else if (animRunning && (rect.top < 0 || rect.bottom < 0 || (rect.height() == height && height != 0 && rect.top > 0))) {
            anim?.cancel()
            animRunning = false
        }
        return false
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isAttachToWindows = false
        anim?.cancel()
    }
}