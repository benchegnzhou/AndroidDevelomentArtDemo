package com.zbc.gymappointment.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class CustomWidget(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        getChildAt(0).layout(200, 200, 560, 560)

    }


//
//    fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
//
//      return  super.setFrame(200,200,560,560)
//    }


}