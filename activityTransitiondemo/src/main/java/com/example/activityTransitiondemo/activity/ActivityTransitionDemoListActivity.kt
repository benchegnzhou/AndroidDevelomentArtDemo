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
import kotlinx.android.synthetic.main.layout_activity_transition_demo_mian.*


/**
 * 转场动画列表页
 */
@Route(path = MOUDLE_AROUTER.ACTIVITY_TRANSITION_DEMO_LIST_ACTIVITY)
class ActivityTransitionDemoListActivity : AppCompatActivity() {
    private lateinit var mListAdapter: HomeListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_transition_demo_mian)
        mListAdapter = HomeListAdapter(R.layout.item_cummon_list, null)
        findViewById<TextView>(R.id.tv_title).text = "自定义转场动画"

        findViewById<View>(R.id.iv_back_view).setOnClickListener {
            finish()
        }

        rv_list.let {
            it.layoutManager = LinearLayoutManager(ActivityTransitionDemoListActivity@ this)
            it.adapter = mListAdapter
        }
//        findViewById<RecyclerView>(R.id.)

        mListAdapter.animationEnable = true
        mListAdapter.setOnItemClickListener { _, _, p2 ->
            when (p2) {
                0 -> ARouter.getInstance()
                    .build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY).navigation()
                1 -> ARouter.getInstance().build(MOUDLE_AROUTER.BITMAP_SHADER_ROUND_VIEW)
                    .navigation()
                2 -> ARouter.getInstance()
                    .build(MOUDLE_AROUTER.OVERRIDE_PENDING_TRANSITION_ACTIVITY2).navigation()
                3 -> ARouter.getInstance().build(MOUDLE_AROUTER.CLIP_DISPATCH_ROUND_WIDGET)
                    .navigation()
                4 -> ARouter.getInstance().build(MOUDLE_AROUTER.ANDROID_SHADER)
                    .navigation()
                5 -> ARouter.getInstance().build(MOUDLE_AROUTER.LINEAR_GRADIENT_VIEW_ACTIVITY)
                    .navigation()
                6 -> ARouter.getInstance().build(MOUDLE_AROUTER.SWEEP_GRADIENTT_VIEW_ACTIVITY)
                    .navigation()
                7 -> ARouter.getInstance().build(MOUDLE_AROUTER.RADIAL_GRADIENTT_VIEW_ACTIVITY)
                    .navigation()
                8 -> ARouter.getInstance().build(MOUDLE_AROUTER.COMPOSE_SHADER_ROUND_VIEW)
                    .navigation()
            }
        }
        initData()
    }

    private fun initData() {
        val list = mutableListOf<String>()
        list.add("通过overridePendingTransition实现转场动画")
        list.add("使用BitmapShader实现自定义圆角")
        list.add("使用Clip的Region.Op参数 实现自定义圆角")
        list.add("使用Clip.disppatch实现自定义圆角")
        list.add("Android中的shader")
        list.add("LinearGradient渐变渲染")
        list.add("SweepGradient扫描/梯度渲染")
        list.add("RadialGradient辐射梯度渲染")
        list.add("ComposeShader组合渲染")
        mListAdapter.data = list
    }


}