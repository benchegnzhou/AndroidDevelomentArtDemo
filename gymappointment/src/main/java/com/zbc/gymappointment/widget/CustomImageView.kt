package com.zbc.gymappointment.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.zbc.gymappointment.test.IAnim

class CustomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int  = 0,
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) ,IAnim{

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

}