package com.example.activityTransitiondemo.activity


import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.activityTransitiondemo.R
import com.example.activityTransitiondemo.contantvalue.MOUDLE_AROUTER


@Route(path = MOUDLE_AROUTER.MATERIAL_DESIGN_EXPLODE_ANIM_ACTIVITY2)
class MaterialDesignExplodeSlideAnimActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 开启Material动画
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design_explode_anim_2)
        supportActionBar?.hide();   //如果标题栏不为空，给它隐藏
        val title = intent.getStringExtra(MaterialDesignExplodeSlideAnimActivity1.TITLE)
        findViewById<TextView>(R.id.tv_title).text =
            title ?: resources.getString(R.string.override_pending_transition_page_1_title)
        findViewById<View>(R.id.iv_back_view).setOnClickListener { finish() }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupWindowAnimations() {

        when (intent.getIntExtra(MaterialDesignExplodeSlideAnimActivity1.TYPE, 1)) {
            1 -> {
                setupWindowAnimationsExplodeJava()
            }
            2 -> {
                setupWindowAnimationsExplodeXml()
            }
            3 -> {
                setupWindowAnimationsFadeJava()
            }
            4 -> {
                setupWindowAnimationsFadeXml()
            }
            5 -> {
                setupWindowReturnAnimations()
            }
            6 -> {
                setupWindowNoReturnAnimations()
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupWindowAnimationsFadeXml() {
        val enterExplode =
            TransitionInflater.from(this).inflateTransition(R.transition.enter_fade_1)
        window.enterTransition = enterExplode

        val exitExplode =
            TransitionInflater.from(this).inflateTransition(R.transition.exit_fade_1)
        window.exitTransition = exitExplode
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupWindowAnimationsFadeJava() {
        val enterFade = Fade()
        enterFade.duration = 1000
        // 设置进入的动画
        window.enterTransition = enterFade


        val exitFade = Fade()
        exitFade.duration = 1000
        // 设置退出动画
        window.exitTransition = exitFade
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupWindowAnimationsExplodeXml() {
        val enterExplode =
            TransitionInflater.from(this).inflateTransition(R.transition.enter_explode_1)
        window.enterTransition = enterExplode

        val exitExplode =
            TransitionInflater.from(this).inflateTransition(R.transition.exit_explode_1)
        window.exitTransition = exitExplode
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupWindowAnimationsExplodeJava() {
        val enterExplode = Explode()
        enterExplode.duration = 1000
        // 设置进入的动画
        window.enterTransition = enterExplode


        val exitExplode = Explode()
        exitExplode.duration = 1000
        // 设置退出动画
        window.exitTransition = exitExplode
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupWindowReturnAnimations() {
        val enterExplode = Explode()
        enterExplode.duration = 1000
        // 设置进入的动画
        window.enterTransition = enterExplode


        val exitExplode = Explode()
        exitExplode.duration = 1000
        // 设置退出动画
        window.exitTransition = exitExplode

        val enterFade = Fade()
        enterFade.duration = 200
        // 设置进入的动画
        window.returnTransition = enterFade

        val reenterExplode = Explode()
        reenterExplode.duration = 200
        // 设置进入的动画
        window.reenterTransition = reenterExplode
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupWindowNoReturnAnimations() {
        val enterExplode = Explode()
        enterExplode.duration = 1000
        // 设置进入的动画
        window.enterTransition = enterExplode


        val exitExplode = Explode()
        exitExplode.duration = 1000
        // 设置退出动画
        window.exitTransition = exitExplode

    }
}