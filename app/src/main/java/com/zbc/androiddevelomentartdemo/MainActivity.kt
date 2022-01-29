package com.zbc.androiddevelomentartdemo

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zbc.androiddevelomentartdemo.activity.IBinderTestActivity
import com.zbc.androiddevelomentartdemo.entity.SystemMsgBean
import com.zbc.androiddevelomentartdemo.entity.UserBean
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 首页
 */
class MainActivity : AppCompatActivity() {

    private val RC_LOCATION_CONTACTS_PERM = 120;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
    }

    private fun loadData() {
        rv_view.layoutManager = LinearLayoutManager(this)
        val data = HomeDataHelper.HOME_DATA_LIST
        data.forEach {
            Log.e("---", it.title!!)
        }
        rv_view.adapter = object : BaseQuickAdapter<HomeDataHelper.HomeBean, BaseViewHolder>(
            R.layout.item_home_view,
            data
        ) {
            override fun convert(holder: BaseViewHolder, item: HomeDataHelper.HomeBean) {
                holder.setText(R.id.tv_title, item.title)
            }
        }.apply {
            setOnItemClickListener { var1, _, var3 ->
                when (var3) {
                    4 -> {
                        Debug.startMethodTracing("tv_ibinder_test");
                        val stringList = ArrayList<String>().apply {
                            add("a")
                            add("b")
                            add("c")
                        }

                        val intent = Intent(this@MainActivity, IBinderTestActivity::class.java)
                            .apply {
                                putExtra("intValue", 12);
                                putExtra("StringValue", "this is String value");
                                putStringArrayListExtra("StringListValue", stringList);
                                putExtra(
                                    "SerializableValue", UserBean(
                                        "小明",
                                        2,
                                        "北京",
                                        UserBean.HomeTown("北京北", "1263.15", "1545.24")
                                    )
                                )
                                putExtra(
                                    "ParcelableValue",
                                    SystemMsgBean("id_123", "推送消息", System.currentTimeMillis(), 0)
                                )
                            }
                        intent.putExtras(Bundle().apply {
                            putInt("intValue", 12)
                            putString("StringValue", "this is String value")
                            putStringArrayList("StringListValue", stringList)
                            putSerializable(
                                "SerializableValue",
                                UserBean(
                                    "小明",
                                    2,
                                    "北京",
                                    UserBean.HomeTown(
                                        "北京北",
                                        "1263.15",
                                        "1545.24"
                                    )
                                )
                            )
                            putParcelable(
                                "ParcelableValue",
                                SystemMsgBean(
                                    "id_123",
                                    "推送消息",
                                    System.currentTimeMillis(),
                                    0
                                )
                            )
                        }
                        )

                        startActivity(intent);
                        Debug.stopMethodTracing();
                    }
                    else -> {
                        (var1.data.get(var3) as HomeDataHelper.HomeBean).apply {
                            startActivity(Intent(this@MainActivity, this.clazz))
                        }
                    }
                }
            }
        }
    }



    private fun requestPermissions() {
        //acitivty中申请权限
        ActivityCompat.requestPermissions(
            this, Array(1) {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, RC_LOCATION_CONTACTS_PERM
        );
    }


    //activity权限授权结果回调
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}