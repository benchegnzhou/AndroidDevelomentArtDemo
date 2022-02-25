package com.example.activityTransitiondemo.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.activityTransitiondemo.R
import com.example.activityTransitiondemo.contantvalue.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_override_pending_transition.*


@Route(path = MOUDLE_AROUTER.CONTENT_TRANSITION_ACTIVITY1)
class ContentTransitionActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();   //如果标题栏不为空，给它隐藏
        setContentView(R.layout.activity_content_transition_1)
        findViewById<TextView>(R.id.tv_title).text =intent.getStringExtra(
            MaterialDesignExplodeSlideAnimActivity1.TITLE
        )

        findViewById<View>(R.id.iv_back_view).setOnClickListener { finish() }
        Glide.with(this).load(R.drawable.bcb218eb665ee2c3fa97a09af36a269a)
            .into(findViewById<ImageView>(R.id.iv_share_elements))
    }

    override fun finish() {
        super.finish()
        //这里添加退出的转场动画
        overridePendingTransition(
            0,
            0
        )
    }

}