package com.example.custom_round_view.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.example.custom_round_view.R

/**
 * 文章参考 https://www.jianshu.com/p/db401403385b
 */
class ProterDuffModeRoundImageView(
    context: Context?,
    @Nullable attrs: AttributeSet,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr) {


    val mPaint by lazy {
        val mPaint: Paint = Paint()
        mPaint.color = 0x00ff0055
        mPaint
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        val decodeResource =
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background)
        canvas?.apply {
            save()
            canvas.drawRect(Rect(100, 100, 500, 500), mPaint)
            mPaint.color = 0x6600ff99
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DARKEN)  //设置图层混合模式
            canvas.drawBitmap(decodeResource, 100F, 110F, mPaint)
            mPaint.xfermode = null   // 用完及时清除 Xfermode
        }
    }


}