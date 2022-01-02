package com.example.custom_round_view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.custom_round_view.R
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_sweep_gradient_view.*
import kotlinx.android.synthetic.main.common_top_title.*

/**
 * SweepGradient extends Shader
 * 参考:https://blog.csdn.net/fengbohz/article/details/19416043?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_title~default-0.pc_relevant_paycolumn_v2&spm=1001.2101.3001.4242.1&utm_relevant_index=3
 */

@Route(path = MOUDLE_AROUTER.SWEEP_GRADIENTT_VIEW_ACTIVITY)
class SweepGradient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sweep_gradient_view)
        tv_title.text = "SweepGradient 扫描/梯度渲染"
        iv_back_view.setOnClickListener { finish() }
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16410935917193.png")
            .into(iv_sweep_gradient_1)
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16410938936202.png")
            .into(iv_sweep_gradient_2)
    }
}