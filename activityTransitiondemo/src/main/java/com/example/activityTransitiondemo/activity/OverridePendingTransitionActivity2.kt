package com.example.activityTransitiondemo.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.activityTransitiondemo.R
import com.example.activityTransitiondemo.contantvalue.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_override_pending_transition.*


@Route(path = MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY2)
class OverridePendingTransitionActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();   //如果标题栏不为空，给它隐藏
        setContentView(R.layout.activity_override_pending_transition)
        findViewById<TextView>(R.id.tv_title).text = "第二个界面"
        findViewById<View>(R.id.iv_back_view).setOnClickListener { finish() }

        view_btn.text = "跳转后的页面"
    }


}