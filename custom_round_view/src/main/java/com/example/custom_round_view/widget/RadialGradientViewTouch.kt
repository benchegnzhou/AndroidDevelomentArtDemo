package com.example.custom_round_view.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ScrollView
import androidx.annotation.Nullable
import kotlin.math.max

/**
 * 参考：https://blog.csdn.net/u010126792/article/details/85245372
 */
class RadialGradientViewTouch(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr), View.OnClickListener, View.OnTouchListener {
    private var currentCorner = 0f
    private var isClockwise = true

    var array = arrayListOf(0.06f, 1.0f).toFloatArray()
    var currentRadius = 0f

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    init {
        setOnClickListener(this)
        setOnTouchListener(this)
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
                touchX,
                touchY,
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
                touchX,
                touchY,
                currentRadius,
                mPaint
            )
            restore()
        }
        drawText(canvas)
    }

    private var touchX = 0f
    private var touchY = 0f

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
//                repeatMode = ValueAnimator.REVERSE
                addUpdateListener { valueAnimator ->
                    currentRadius = valueAnimator.animatedValue as Float
                    invalidate()
                }
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        animRunning = false
                        currentRadius = 0f
                        invalidate()
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                        animRunning = false
                        currentRadius = 0f
                        invalidate()
                    }

                    override fun onAnimationRepeat(p0: Animator?) {

                    }
                })
            }
        anim?.start()
        animRunning = true
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isAttachToWindows = false
        anim?.cancel()
    }

    override fun onTouch(view: View?, enent: MotionEvent?): Boolean {
        when (enent?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (anim?.isRunning == true) {
                    anim?.cancel()
                    animRunning = false
                }
                touchX = enent.x
                touchY = enent.y
                startAnim()
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }
}