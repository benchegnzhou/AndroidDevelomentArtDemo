package com.example.activityTransitiondemo.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.activityTransitiondemo.R
import com.example.activityTransitiondemo.contantvalue.MOUDLE_AROUTER
import kotlinx.android.synthetic.main.activity_material_design_explode_anim_1.*
import kotlinx.android.synthetic.main.activity_override_pending_transition.*
import kotlinx.android.synthetic.main.activity_override_pending_transition.view_btn


@Route(path = MOUDLE_AROUTER.MATERIAL_DESIGN_EXPLODE_ANIM_ACTIVITY1)
class MaterialDesignExplodeSlideAnimActivity1 : AppCompatActivity() {
    companion object {
        const val TYPE = "type"
        const val TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design_explode_anim_1)
        supportActionBar?.hide();   //如果标题栏不为空，给它隐藏
        findViewById<TextView>(R.id.tv_title).text =
            resources.getString(R.string.material_design_explode_slide_anim_activity_page_1_title)
        findViewById<View>(R.id.iv_back_view).setOnClickListener { finish() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTranslucent(true)
        }
//        view_btn.setOnClickListener {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                startActivity(
//                    Intent(this, MaterialDesignExplodeSlideAnimActivity2::class.java),
//                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
//                )
//            }
//        }
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16456232807348.png")
            .into(findViewById<ImageView>(R.id.iv_pending_transition_1))
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16456233522491.png")
            .into(findViewById<ImageView>(R.id.iv_pending_transition_2))

        Glide.with(this).load("http://minio.898311.xyz:8900/blogfile/16456235643068.awebp")
            .into(findViewById<ImageView>(R.id.iv_pending_transition_3))
        Glide.with(this).load("http://minio.898311.xyz:8900/blogfile/16456236148585.awebp")
            .into(findViewById<ImageView>(R.id.iv_pending_transition_4))
        Glide.with(this).load("http://minio.898311.xyz:8900/blogfile/16456236508066.awebp")
            .into(findViewById<ImageView>(R.id.iv_pending_transition_5))
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16457939658428.png")
            .into(findViewById<ImageView>(R.id.iv_pending_transition_8))
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16457961816454.png")
            .into(findViewById<ImageView>(R.id.iv_pending_transition_9))
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16457972767228.png")
            .into(findViewById<ImageView>(R.id.iv_pending_transition_10))
        Glide.with(this).load("http://minio.898311.xyz:8900/blogimg/16457973673675.png")
            .into(findViewById<ImageView>(R.id.iv_pending_transition_10))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadMessage()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun loadMessage() {
        tv_pending_transition_5.text = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<fade xmlns:android=\"http://schemas.android.com/apk/res/\"\n" +
                "    android:duration=\"1000\"/>"
        tv_pending_transition_7.text = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<slide xmlns:android=\"http://schemas.android.com/apk/res/\"\n" +
                "    android:duration=\"1000\"/>"
        tv_pending_transition_9.text = "getWindow().setExitTransition(slide);\n" +
                "\t  @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_transition);\n" +
                "        setupWindowAnimations();\n" +
                "    }\n" +
                "\n" +
                "    private void setupWindowAnimations() {\n" +
                "        Slide slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);\n" +
                "        getWindow().setExitTransition(slide);\n" +
                "    }\n"
        tv_pending_transition_11.text = "\t  @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_transition);\n" +
                "        setupWindowAnimations();\n" +
                "    }\n" +
                "\n" +
                "    private void setupWindowAnimations() {\n" +
                "        Fade fade = TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);\n" +
                "        getWindow().setEnterTransition(fade);\n" +
                "    }\n"
        tv_pending_transition_14.text = "  @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_transition);\n" +
                "        setupWindowAnimations();\n" +
                "    }\n" +
                "\n" +
                "    private void setupWindowAnimations() {\n" +
                "        Slide slide = new Slide();\n" +
                "        slide.setDuration(1000);\n" +
                "        getWindow().setExitTransition(slide);\n" +
                "    }"
        tv_pending_transition_20.text =
            "Return 和Reenter Transition是enter 和exit相反的过程。当从Activity A进入到Activity B时会执行 exit和enter当从Activity B退回到Activity A时会执行Return Transition和Reenter Transition。\n" +
                    "\n" +
                    "\n" +
                    "EnterTransition < -- > ReturnTransition\n" +
                    "\n" +
                    "\n" +
                    "ExitTransition < -- > ReenterTransition\n" +
                    "如果没有定义Return 或者 Reenter，那么Android会反向执行Enter和Exit变换。如下图从Activity B退回到Activity A："
        tv_pending_transition_16.text = "\t  @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_transition);\n" +
                "        setupWindowAnimations();\n" +
                "    }\n" +
                "\n" +
                "    private void setupWindowAnimations() {\n" +
                "        Fade fade = new Fade();\n" +
                "        fade.setDuration(1000);\n" +
                "        getWindow().setEnterTransition(fade);\n" +
                "    }"
        tv_pending_transition_19.text = "ReturnTransition & ReenterTransition"
        tv_pending_transition_22.text = "   @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_transition);\n" +
                "        setupWindowAnimations();\n" +
                "    }\n" +
                "\n" +
                "    private void setupWindowAnimations() {\n" +
                "        Fade fade = new Fade();\n" +
                "        fade.setDuration(1000);\n" +
                "        getWindow().setEnterTransition(fade);\n" +
                "        \n" +
                "        Slide slide = new Slide();\n" +
                "        slide.setDuration(1000);\n" +
                "        getWindow().setReturnTransition(slide);        \n" +
                "    }"
        tv_pending_transition_28.text = "·  changeBounds  改变目标布局中view的边界\n" +
                "·  changeClipBounds  裁剪目标布局中view的边界\n" +
                "·  changeTransform  实现旋转或者缩放动画\n" +
                "·  changeImageTransform  实现目标布局中ImageView的旋转或者缩放动画\n" +
                "\n" +
                "实现上面的效果需要三个步骤：\n"

        tv_pending_transition_31.text =
            "<style name=\"MaterialAnimations\" parent=\"@style/Theme.AppCompat.Light.NoActionBar\">\n" +
                    "    ...\n" +
                    "    <item name=\"android:windowContentTransitions\">true</item\n" +
                    "    ...\n" +
                    "</style>"

        tv_pending_transition_34.text = "<ImageView\n" +
                "        android:id=\"@+id/small_blue_icon\"\n" +
                "        style=\"@style/MaterialAnimations.Icon.Small\"\n" +
                "        android:src=\"@drawable/circle\"\n" +
                "        android:transitionName=\"@string/blue_name\" />\n"
        tv_pending_transition_36.text = "<ImageView\n" +
                "        android:id=\"@+id/big_blue_icon\"\n" +
                "        style=\"@style/MaterialAnimations.Icon.Big\"\n" +
                "        android:src=\"@drawable/circle\"\n" +
                "        android:transitionName=\"@string/blue_name\" />\n"
        tv_pending_transition_39.text =
            "blueIconImageView.setOnClickListener(new View.OnClickListener() {\n" +
                    "    @Override\n" +
                    "    public void onClick(View v) {\n" +
                    "        Intent i = new Intent(MainActivity.this, SharedElementActivity.class);\n" +
                    "\n" +
                    "        View sharedView = blueIconImageView;\n" +
                    "        String transitionName = getString(R.string.blue_name);\n" +
                    "\n" +
                    "        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, sharedView, transitionName);\n" +
                    "        startActivity(i, transitionActivityOptions.toBundle());\n" +
                    "    }\n" +
                    "});\n"



        startActivityWithAnim(
            tv_title_1, "使用java代码创建explode"
        ) {
            startActivity(
                Intent(
                    MaterialDesignExplodeSlideAnimActivity1@ this,
                    MaterialDesignExplodeSlideAnimActivity2::class.java
                ).apply {
                    putExtra(TYPE, 1)
                    putExtra(TITLE, "使用java代码创建explode")
                },
                ActivityOptions.makeSceneTransitionAnimation(
                    MaterialDesignExplodeSlideAnimActivity1@ this
                ).toBundle()
            )
        }

        startActivityWithAnim(
            tv_title_2, "使用xml代码创建explode"
        ) {
            startActivity(
                Intent(
                    MaterialDesignExplodeSlideAnimActivity1@ this,
                    MaterialDesignExplodeSlideAnimActivity2::class.java
                ).apply {
                    putExtra(TYPE, 2)
                    putExtra(TITLE, "使用xml代码创建explode")
                },
                ActivityOptions.makeSceneTransitionAnimation(
                    MaterialDesignExplodeSlideAnimActivity1@ this
                ).toBundle()
            )
        }

        startActivityWithAnim(
            tv_title_3, "使用java代码创建Fade"
        ) {
            startActivity(
                Intent(
                    MaterialDesignExplodeSlideAnimActivity1@ this,
                    MaterialDesignExplodeSlideAnimActivity2::class.java
                ).apply {
                    putExtra(TYPE, 3)
                    putExtra(TITLE, "使用java代码创建Fade")
                },
                ActivityOptions.makeSceneTransitionAnimation(
                    MaterialDesignExplodeSlideAnimActivity1@ this
                ).toBundle()
            )
        }

        startActivityWithAnim(
            tv_title_4, "使用xml代码创建Fade"
        ) {
            startActivity(
                Intent(
                    MaterialDesignExplodeSlideAnimActivity1@ this,
                    MaterialDesignExplodeSlideAnimActivity2::class.java
                ).apply {
                    putExtra(TYPE, 4)
                    putExtra(TITLE, "使用xml代码创建Fade")
                },
                ActivityOptions.makeSceneTransitionAnimation(
                    MaterialDesignExplodeSlideAnimActivity1@ this
                ).toBundle()
            )
        }

        startActivityWithAnim(
            tv_title_5, "重写Return Transition"
        ) {
            startActivity(
                Intent(
                    MaterialDesignExplodeSlideAnimActivity1@ this,
                    MaterialDesignExplodeSlideAnimActivity2::class.java
                ).apply {
                    putExtra(TYPE, 5)
                    putExtra(TITLE, "重写Return Transition")
                },
                ActivityOptions.makeSceneTransitionAnimation(
                    MaterialDesignExplodeSlideAnimActivity1@ this
                ).toBundle()
            )
        }

        startActivityWithAnim(
            tv_title_6, "不重写Return Transition"
        ) {
            startActivity(
                Intent(
                    MaterialDesignExplodeSlideAnimActivity1@ this,
                    MaterialDesignExplodeSlideAnimActivity2::class.java
                ).apply {
                    putExtra(TYPE, 6)
                    putExtra(TITLE, "不重写Return Transition")
                },
                ActivityOptions.makeSceneTransitionAnimation(
                    MaterialDesignExplodeSlideAnimActivity1@ this
                ).toBundle()
            )
        }



        startActivityWithAnim(
            tv_title_10, "shared element 动画"
        ) {
            startActivity(
                Intent(
                    MaterialDesignExplodeSlideAnimActivity1@ this,
                    ContentTransitionActivity1::class.java
                ).apply {
                    putExtra(TYPE, 7)
                    putExtra(TITLE, "shared element 动画")
                },
                ActivityOptions.makeSceneTransitionAnimation(
                    MaterialDesignExplodeSlideAnimActivity1@ this,
                    iv_content_transition,
                    MaterialDesignExplodeSlideAnimActivity1@ this.resources.getString(R.string.content_transition_key)
                ).toBundle()
            )
        }


    }

    private fun startActivityWithAnim(v: TextView, title: String, block: () -> Unit) {
        v.let {
            it.text = title
            it.setOnClickListener {
                block()
            }
        }
    }


}