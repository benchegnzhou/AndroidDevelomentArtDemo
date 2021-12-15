package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.Nullable

/**
 * 参考链接：https://blog.csdn.net/u010126792/article/details/85253038
 */
class ComposeShaderRoundView(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) : RelativeLayout(context, attrs, defStyleAttr) {

    val mPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG) }
    var mShader: BitmapShader? = null;
    val BALL_MAX_X = 100f
    val BALL_MAX_Y = 50f
    var right = true
    var bottom = true
    var current_ballX = 100f
    var current_ballY = 80f

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    init {
        //禁止硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        //解决viewgroup自己不会draw问题https://blog.csdn.net/sinat_26710701/article/details/71171726
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!isAttachToWindows) {
            return
        }
        canvas?.apply {
            if (mShader == null) {
                mShader =
                    BitmapShader(destBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

            }
            mPaint.shader = mShader
            //绘制圆角矩形
            drawRoundRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                50f,
                50f,
                mPaint
            )

            //绘制小圆球
            val mShade =
                BitmapShader(ballBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            mPaint.shader = mShade


            drawCircle(current_ballX, current_ballY, 56f, mPaint)
            if (right) {
                if (current_ballX > (width - BALL_MAX_X)) {
                    right = false
                    current_ballX -= 1
                } else {
                    current_ballX += 1
                }
            } else {
                if (current_ballX < (BALL_MAX_X)) {
                    right = true
                    current_ballX += 1
                } else {
                    current_ballX -= 1
                }
            }
            if (bottom) {
                if (current_ballY > (height - BALL_MAX_Y)) {
                    bottom = false
                    current_ballY -= 1
                } else {
                    current_ballY += 1
                }
            } else {
                if (current_ballY < (BALL_MAX_Y)) {
                    bottom = true
                    current_ballY += 1
                } else {
                    current_ballY -= 1
                }
            }
        }
        if (isAttachToWindows) {
            postDelayed({
                invalidate()
            }, 10)
        }
//        mPaint.shader = null
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

    val ballBitmap by lazy {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val paint = Paint()

        paint.color = Color.parseColor("#ff6E88E5")
        Canvas(bitmap).drawRect(
            0f,
            0f,
            bitmap.width.toFloat(),
            bitmap.height.toFloat(),
            paint
        )
        bitmap
    }

    var isAttachToWindows = false;
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