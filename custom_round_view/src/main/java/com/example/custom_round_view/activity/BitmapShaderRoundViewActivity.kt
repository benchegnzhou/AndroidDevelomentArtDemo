package com.example.custom_round_view.activity

import android.os.Bundle
import android.os.Trace
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.custom_round_view.R
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.common_top_title.*
import java.lang.reflect.Method

/**
 * 参考
 *
 */
@Route(path = MOUDLE_AROUTER.BITMAP_SHADER_ROUND_VIEW)
class BitmapShaderRoundViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        open_trace()
//        Trace.traceBegin("BitmapShaderRoundViewActivity")
        trace_begin("android.os.Trace","traceBegin")
        doSomeThing()
        setContentView(R.layout.activity_bitmap_shader_round_view)
//        Trace.traceEnd("BitmapShaderRoundViewActivity")
        trace_end("android.os.Trace","traceEnd")
        tv_title.text = "通过 BitmapShader 实现自定义圆角"
        iv_back_view.setOnClickListener { finish() }
//        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16383650433326.png")
//            .into(iv_clip_path_1)


    }

    fun open_trace(){
        val trace = Class.forName("android.os.Trace")
        val setAppTracingAllowed = trace.getDeclaredMethod(
            "setAppTracingAllowed",
            Boolean::class.javaPrimitiveType
        )
        setAppTracingAllowed.invoke(null, true)
    }

    fun trace_begin(class_name: String, mathod_name: String) {
        val cls = Class.forName(class_name)
        val m: Method = cls.getDeclaredMethod(
            mathod_name, Long::class.java,
            String::class.java
        )

        m.invoke(cls, 20, "doSomeThing")
    }

    fun trace_end(class_name: String, mathod_name: String) {
        val cls = Class.forName(class_name)
        val m: Method = cls.getDeclaredMethod(mathod_name,Long::class.java)
        m.isAccessible = true
        m.invoke(cls,20)
    }

    private fun doSomeThing() {
        //
        Log.e("BitmapShaderRoundView", "doSomeThing")
        Thread.sleep(2000)
    }
}