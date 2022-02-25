package com.example.activityTransitiondemo.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.activityTransitiondemo.R
import com.example.activityTransitiondemo.contantvalue.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_override_pending_transition.*


@Route(path = MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY)
class OverridePendingTransitionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_override_pending_transition)
        supportActionBar?.hide();   //如果标题栏不为空，给它隐藏
        findViewById<TextView>(R.id.tv_title).text = resources.getString(R.string.override_pending_transition_page_1_title)
        findViewById<View>(R.id.iv_back_view).setOnClickListener { finish() }

        view_btn.setOnClickListener {
            ARouter.getInstance().build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY2)
                .navigation()

            overridePendingTransition(
                R.anim.enter_override_pending_transition_activity,
                R.anim.exit_override_pending_transition_activity
            )
        }
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16454996636347.png")
            .into(findViewById<ImageView>(R.id.iv_pending_transition_1))

    }


}