package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable

class BitmapShaderRoundView(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) : View(context, attrs, defStyleAttr) {

    val mPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG) }
    var mShader: BitmapShader? = null;

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    init {
        //禁止硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            val drawable: BitmapDrawable = getDrawable() as BitmapDrawable
            if (mShader == null) {
                mShader =
                    BitmapShader(drawable.getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

            }
            mPaint.shader = mShader

            drawRoundRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                50f,
                50f,
                mPaint
            )
        }
        mPaint.shader = null
    }

    private fun getDrawable(): BitmapDrawable {

    }


}