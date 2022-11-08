package com.zbc.gymappointment.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zbc.gymappointment.R

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity_layout)
        findViewById<View>(R.id.bg_iv).apply {
            setOnClickListener {
                this.scaleX = 1.2f
                this.scaleY = 1.2f
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log
            .e("------", "TestActivity - outState.hashcode = ${outState.hashCode()}")
    }

}