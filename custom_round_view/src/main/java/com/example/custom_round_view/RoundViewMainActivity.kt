package com.example.custom_round_view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.custom_round_view.adapter.MainListAdapter
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_round_view_main.*
import kotlinx.android.synthetic.main.common_top_title.*

@Route(path = MOUDLE_AROUTER.ROUNDVIEW_MAIN_ACTIVITY)
class RoundViewMainActivity : AppCompatActivity() {
    lateinit var mListAdapter: MainListAdapter
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
        mListAdapter.setOnItemClickListener { p0, p1, p2 -> }
        initData()
    }

    private fun initData() {
        var list = mutableListOf<String>()
        list.add("使用shape实现自定义圆角")
        mListAdapter.data = list
    }


}