package com.example.kotlincoroutinedemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.kotlincoroutinedemo.constant.RouterPath
import com.google.gson.Gson
import com.google.gson.JsonElement

@Route(path = RouterPath.KOTLIN_COROUTINE_HOME_LIST_AT )
class KotlinCoroutineHomeList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_coroutine_home_list)
    }

    fun generic(){
        startActivity<KotlinCoroutineHomeList>(context =baseContext )
    }

    inline fun <reified T: Any> Gson.fromJson(json: JsonElement): T = this.fromJson(json, T::class.java)

    private inline fun <reified activity:Activity> startActivity(context: Context){
        context.startActivity(Intent(context,activity::class.java))
    }

    inline fun <reified T:Any> Gson2.from(json: JsonElement):String =  Gson2().from(json,T::class.java)
}

class Gson2{
    fun <S:String> from(json: JsonElement, T: Any):S {
        Gson().fromJson(json, T::class.java)
        return "" as S
    }
}