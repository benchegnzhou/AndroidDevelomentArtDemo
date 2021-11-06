package com.example.custom_round_view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.custom_round_view.constant.MOUDLE_AROUTER

@Route(path = "/custom_round_view/RoundViewMainActivity")
class RoundViewMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round_view_main)
    }
}