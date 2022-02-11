package com.example.activityTransitiondemo.activity

import android.os.Bundle
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
        view_btn.setOnClickListener {
            ARouter.getInstance().build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY2).navigation()
            overridePendingTransition(R.anim.enter_override_pending_transition_activity,R.anim.out_override_pending_transition_activity)
        }
    }


}