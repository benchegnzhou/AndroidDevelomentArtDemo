package com.example.kotlincoroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.kotlincoroutinedemo.constant.RouterPath
import kotlinx.coroutines.delay
@Route(path = RouterPath.KOTLIN_COROUTINE_HOME_LIST_AT )
class KotlinCoroutineHomeList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutine_home_list)
    }


}