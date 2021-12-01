package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable

/**
 * 使用 PorterDuffColor绘制圆角矩形
 */
class PorterDuffColorFilterRoundImageView(
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
        val mPaint = Paint()
        mPaint.color = Color.parseColor("#AB47BC")
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.apply {
            save()
            val path = Path()
            path.fillType = Path.FillType.INVERSE_EVEN_ODD
            path.addRoundRect(RectF(0f, 0f, 200f, 200f), 50f, 50f, Path.Direction.CW)
            clipPath(path)
            restore()
        }
    }


}