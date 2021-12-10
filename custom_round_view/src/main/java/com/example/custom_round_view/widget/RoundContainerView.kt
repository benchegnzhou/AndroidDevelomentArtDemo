package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.annotation.Nullable

class RoundContainerView(
    context: Context?,
    @Nullable attrs: AttributeSet?,
    defStyleAttr: Int
) : RelativeLayout(context, attrs, defStyleAttr) {

    val mPaintFlagsDrawFilter by lazy {
        PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    }

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : this(context, attrs, 0)

    init {
        //禁止硬件加速
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }


    /**
     * 父控件在dispatchDraw之前，裁剪canvas限制子控件的canvas样式
     */
    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.apply {
            val path = Path()
            path.fillType = Path.FillType.INVERSE_EVEN_ODD
            path.addRoundRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                50f,
                50f,
                Path.Direction.CCW
            )
            canvas.drawFilter = mPaintFlagsDrawFilter
            clipPath(path, Region.Op.DIFFERENCE)
        }
        super.dispatchDraw(canvas)
    }
}