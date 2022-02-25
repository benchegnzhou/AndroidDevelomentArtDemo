package com.example.activityTransitiondemo.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.activityTransitiondemo.R
import com.example.activityTransitiondemo.contantvalue.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_override_pending_transition.*


@Route(path = MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY4)
class OverridePendingTransitionActivity4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();   //如果标题栏不为空，给它隐藏
        setContentView(R.layout.activity_override_pending_transition_2)
        findViewById<TextView>(R.id.tv_title).text =
            resources.getString(R.string.override_pending_transition_page_2_title)
        findViewById<View>(R.id.iv_back_view).setOnClickListener { finish() }

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