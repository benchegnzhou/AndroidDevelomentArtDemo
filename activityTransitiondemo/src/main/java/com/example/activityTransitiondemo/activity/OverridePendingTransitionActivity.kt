package com.example.activityTransitiondemo.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.activityTransitiondemo.R
import com.example.activityTransitiondemo.contantvalue.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_override_pending_transition.*



@Route(path = MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY)
class OverridePendingTransitionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_override_pending_transition)
        findViewById<TextView>(R.id.tv_title).text = "第一个界面"
        findViewById<View>(R.id.iv_back_view).setOnClickListener { finish() }
        view_btn.text = "A启动B： A发生exit动画，B发生enter动画\nB返回A：B发生return动画，A发生reenter动画"
        view_btn.setOnClickListener {
            ARouter.getInstance().build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY2).navigation()
            overridePendingTransition(R.anim.enter_override_pending_transition_activity,R.anim.exit_override_pending_transition_activity)
        }
    }


}