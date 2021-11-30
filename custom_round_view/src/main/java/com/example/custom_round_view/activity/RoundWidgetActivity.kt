package com.example.custom_round_view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.custom_round_view.R
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_round_widget.*
import kotlinx.android.synthetic.main.common_top_title.*

@Route(path = MOUDLE_AROUTER.ROUND_WIDGET_ACTIVITY)
class RoundWidgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_widget)
        iv_back_view.setOnClickListener {  finish() }

        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16382723810737.png").into(iv_proter_duff_mode)
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16382750066097.gif").into(iv_proter_duff_mode_2)
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16382751465269.gif").into(iv_proter_duff_mode_3)
//        System.out.println(MyClass().demoPrint())
//        System.out.println(ObjectSizeFetcher.getObjectSize("size = " + MyClass()))
    }
}