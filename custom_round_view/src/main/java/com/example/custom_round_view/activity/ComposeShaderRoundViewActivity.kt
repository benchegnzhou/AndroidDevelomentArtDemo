package com.example.custom_round_view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.custom_round_view.R
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.common_top_title.*

/**
 * 参考:https://blog.csdn.net/u010126792/article/details/85253038
 *
 */
@Route(path = MOUDLE_AROUTER.COMPOSE_SHADER_ROUND_VIEW)
class ComposeShaderRoundViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_shader_round_view)
        tv_title.text = "使用composeShader实现自定义圆角"
        iv_back_view.setOnClickListener { finish() }
//        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16383650433326.png")
//            .into(iv_clip_path_1)

    }
}