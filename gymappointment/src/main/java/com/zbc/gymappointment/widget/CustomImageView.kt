package com.zbc.gymappointment.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import com.zbc.gymappointment.test.IAnim

class CustomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr), IAnim {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec0 = widthMeasureSpec
        var heightMeasureSpec0 = heightMeasureSpec
        widthMeasureSpec0 = MeasureSpec.makeMeasureSpec(200, MeasureSpec.EXACTLY)
        heightMeasureSpec0 = MeasureSpec.makeMeasureSpec(200, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec0, heightMeasureSpec0)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setBackgroundColor(Color.BLUE)
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        return super.setFrame(l, t, r, b)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val str: String? = "benchengzhou"

        with(str) {
            this + "1"
            this + "1"
            this + "1"
        }

        Log.e("----", str ?: "")
    }

    fun addOption(a: String?, default: String?) = when (a) {
        null -> default
        else -> default
    }

    @kotlin.internal.InlineOnly
    open inline fun <T, R> with(receiver: T, block: T.() -> R) = receiver.block()

    @kotlin.internal.inlineOnly
    open inline fun <T,R> T.run(block:T.()->R):R=block()

}