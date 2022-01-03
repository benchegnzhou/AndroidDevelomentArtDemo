package com.example.custom_round_view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.custom_round_view.R
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_compose_shader_round_view.*
import kotlinx.android.synthetic.main.common_top_title.*

/**
 * 参考:https://blog.csdn.net/t12x3456/article/details/10432935
 * https://blog.csdn.net/u010126792/article/details/85253038
 *
 */
@Route(path = MOUDLE_AROUTER.COMPOSE_SHADER_ROUND_VIEW)
class ComposeShaderRoundViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_shader_round_view)
        tv_title.text = "ComposeShader 组合渲染"
        iv_back_view.setOnClickListener { finish() }
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16411421254559.png")
            .into(iv_compose_shader_1)
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16412123352512.png")
            .into(iv_compose_shader_2)
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16411427361309.png")
            .into(iv_compose_shader_3)
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16412130678106.png")
            .into(iv_compose_shader_4)
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16412140002679.png")
            .into(iv_compose_shader_5)
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16412141561412.png")
            .into(iv_compose_shader_6)

    }
}