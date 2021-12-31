package com.example.custom_round_view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.custom_round_view.R
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_linear_gradient_view.*
import kotlinx.android.synthetic.main.common_top_title.*

/**
 * 参考:https://www.jianshu.com/p/a9d09cb7577f
 *
 */
@Route(path = MOUDLE_AROUTER.LINEAR_GRADIENT_VIEW_ACTIVITY)
class LinearGradientViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_gradient_view)
        tv_title.text = "通过 BitmapShader 实现自定义圆角"
        iv_back_view.setOnClickListener { finish() }
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16406784771690.png")
            .into(iv_linear_gradient_1)

        tv_btn_1.setOnClickListener {
            linegrad_1.changePathFillMode()
        }
    }
}