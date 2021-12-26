package com.example.custom_round_view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.example.custom_round_view.R
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_android_shader.*
import kotlinx.android.synthetic.main.activity_region_op_layout.iv_clip_path_1
import kotlinx.android.synthetic.main.common_top_title.*

/**
 * 参考：https://juejin.cn/entry/6844903684447420423?share_token=339d6e9e-e7d7-463a-afed-0d7c9a93e2ce
 *
 */
@Route(path = MOUDLE_AROUTER.ANDROID_SHADER)
class AndroidShaderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_shader)
        tv_title.text = "Android 中的shader"
        iv_back_view.setOnClickListener { finish() }
        Glide.with(this)
            .load("https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/gold-user-assets/2018/9/26/16615502120a7843~tplv-t2oaga2asx-watermark.awebp")
            .into(iv_clip_path_1)
        Glide.with(this)
            .load("https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/gold-user-assets/2018/9/26/16615502122b7e69~tplv-t2oaga2asx-watermark.awebp")
            .into(iv_clip_path_2)
        Glide.with(this)
            .load("https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/gold-user-assets/2018/9/26/166155021232ab48~tplv-t2oaga2asx-watermark.awebp")
            .into(iv_clip_path_3)
        Glide.with(this)
            .load("https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/gold-user-assets/2018/9/26/16615502125f7856~tplv-t2oaga2asx-watermark.awebp")
            .into(iv_clip_path_4)
        Glide.with(this)
            .load("https://p1-jj.byteimg.com/tos-cn-i-t2oaga2asx/gold-user-assets/2018/9/26/1661550234984b29~tplv-t2oaga2asx-watermark.awebp")
            .into(iv_clip_path_5)
        Glide.with(this)
            .load(R.drawable.sadeqjedhqwj)
            .into(iv_clip_path_6)

    }
}