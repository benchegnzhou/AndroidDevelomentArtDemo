package com.example.activityTransitiondemo.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.activityTransitiondemo.R
import com.example.activityTransitiondemo.adapter.HomeListAdapter
import com.example.activityTransitiondemo.contantvalue.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_override_pending_transition_3.*


@Route(path = MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY3)
class OverridePendingTransitionActivity3 : AppCompatActivity() {

    private lateinit var mListAdapter: HomeListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_override_pending_transition_3)
        supportActionBar?.hide();   //如果标题栏不为空，给它隐藏
        mListAdapter = HomeListAdapter(R.layout.item_cummon_list, null)
        findViewById<TextView>(R.id.tv_title).text = "自定义转场动画"

        findViewById<View>(R.id.iv_back_view).setOnClickListener {
            finish()
        }

        rv_list.let {
            it.layoutManager = LinearLayoutManager(ActivityTransitionDemoListActivity@ this)
            it.adapter = mListAdapter
        }


        mListAdapter.animationEnable = true
        mListAdapter.setOnItemClickListener { _, _, p2 ->
            when (p2) {
                0 -> {
                    ARouter.getInstance()
                        .build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY4).navigation()
                    overridePendingTransition(
                        R.anim.enter_override_pending_transition_to_top,
                        R.anim.exit_override_pending_transition_to_top
                    )
                }
                1 -> {
                    ARouter.getInstance()
                        .build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY4)
                        .navigation()
                    overridePendingTransition(
                        R.anim.enter_override_pending_transition_to_bottom,
                        R.anim.exit_override_pending_transition_to_bottom
                    )
                }
                2 -> {
                    ARouter.getInstance()
                        .build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY4).navigation()
                    overridePendingTransition(
                        R.anim.enter_override_pending_transition_to_left,
                        R.anim.exit_override_pending_transition_to_left
                    )
                }
                3 -> {
                    ARouter.getInstance()
                        .build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY4)
                        .navigation()
                    overridePendingTransition(
                        R.anim.enter_override_pending_transition_to_right,
                        R.anim.exit_override_pending_transition_to_right
                    )
                }
                4 -> {
                    ARouter.getInstance()
                        .build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY4)
                        .navigation()
                    overridePendingTransition(
                        R.anim.enter_override_pending_transition_to_scale_center,
                        R.anim.exit_override_pending_transition_to_alpha_center
                    )
                }
                5 -> {
                    ARouter.getInstance()
                        .build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY4)
                        .navigation()
                    overridePendingTransition(
                        R.anim.enter_override_pending_transition_to_scale_lefttop,
                        R.anim.exit_override_pending_transition_to_alpha_center
                    )
                }
                6 -> {
                    ARouter.getInstance()
                        .build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY4)
                        .navigation()
                    overridePendingTransition(
                        R.anim.enter_override_pending_transition_group,
                        R.anim.exit_override_pending_transition_to_alpha_center
                    )
                }
                7 -> {
                    ARouter.getInstance()
                        .build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY4)
                        .navigation()
                    overridePendingTransition(
                        R.anim.enter_override_pending_transition_to_scale_alpha_center,
                        R.anim.exit_override_pending_transition_to_lefttop
                    )
                }

            }
        }
        initData()
    }

    private fun initData() {
        val list = mutableListOf<String>()
        list.add("向上转场")
        list.add("向下转场")
        list.add("向左转场")
        list.add("向右转场")
        list.add("透明淡出，中间缩放进入动画")
        list.add("透明淡出，左上角放大进入")
        list.add("组合动画，旋转、缩放、移动")
        list.add("左上角退出，外围缩放淡入动画")
        list.add("以点击为中心水波纹散开")
        list.add("分解")
        list.add("滑动")
        list.add("淡入淡出")
        mListAdapter.data = list
    }


}