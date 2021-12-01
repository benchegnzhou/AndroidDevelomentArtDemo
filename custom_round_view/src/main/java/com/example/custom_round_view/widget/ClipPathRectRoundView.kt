package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable

/**
 * 参考链接
 * https://blog.csdn.net/eyishion/article/details/53728913
 */
class ClipPathRectRoundView(
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
        //禁止硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

    }

    val mPaint by lazy {
        val paint = Paint()
        paint.color = Color.parseColor("#AB47BC")
        paint.isAntiAlias = true
        paint
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.apply {
            save()
            val path = Path()
            path.fillType = Path.FillType.INVERSE_EVEN_ODD
            path.addRoundRect(RectF(0f, 0f,  width.toFloat(), height.toFloat()), 50f, 50f, Path.Direction.CCW)
            clipPath(path,Region.Op.DIFFERENCE)
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
            restore()
        }
    }
}