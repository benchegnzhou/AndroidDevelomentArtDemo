package com.example.custom_round_view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.custom_round_view.adapter.MainListAdapter
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_round_view_main.*
import kotlinx.android.synthetic.main.common_top_title.*

@Route(path = MOUDLE_AROUTER.ROUNDVIEW_MAIN_ACTIVITY)
class RoundViewMainActivity : AppCompatActivity() {
    private lateinit var mListAdapter: MainListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_view_main)
        mListAdapter = MainListAdapter(R.layout.item_cummon_list, null)
        tv_title.text = "自定义实现控件圆角"
        iv_back_view.setOnClickListener {
            finish()
        }
        rv_view.layoutManager = LinearLayoutManager(this)
        rv_view.adapter = mListAdapter
        mListAdapter.animationEnable = true
        mListAdapter.setOnItemClickListener { _, _, p2 ->
            when (p2) {
                0 -> ARouter.getInstance().build(MOUDLE_AROUTER.ROUND_WIDGET_ACTIVITY).navigation()
                1 -> ARouter.getInstance().build(MOUDLE_AROUTER.BITMAP_SHADER_ROUND_VIEW)
                    .navigation()
                2 -> ARouter.getInstance().build(MOUDLE_AROUTER.REGION_OP_ACTIVITY).navigation()
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
        list.add("使用shape实现自定义圆角")
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