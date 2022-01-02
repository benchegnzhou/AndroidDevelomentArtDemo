package com.example.custom_round_view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.custom_round_view.R
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_radial_gradient_view.*
import kotlinx.android.synthetic.main.common_top_title.*

/**
 * 参考:
 *
 */
@Route(path = MOUDLE_AROUTER.RADIAL_GRADIENTT_VIEW_ACTIVITY)
class RadialGradientViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radial_gradient_view)
        tv_title.text = "RadialGradient 实现绘制辐射渐变效果"
        iv_back_view.setOnClickListener { finish() }
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16411150563223.png")
            .into(iv_radial_gradient_1)

//        tv_btn_1.setOnClickListener {
//            linegrad_1.changePathFillMode()
//        }
    }
}