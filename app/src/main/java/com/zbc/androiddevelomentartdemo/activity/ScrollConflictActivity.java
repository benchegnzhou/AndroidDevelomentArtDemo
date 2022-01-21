package com.zbc.androiddevelomentartdemo.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zbc.androiddevelomentartdemo.R;
import com.zbc.androiddevelomentartdemo.adapter.AppAdapter;
import com.zbc.androiddevelomentartdemo.view.HorizontalScrollView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by benchengzhou on 2019/4/19  13:44 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： android事件冲突解决Demo
 * 类    名： ScrolllConflictActivity
 * 备    注：
 */

public class ScrollConflictActivity extends AppCompatActivity {

    @BindView(R.id.view_111)
    RecyclerView view111;
    @BindView(R.id.view_222)
    RecyclerView view222;
    @BindView(R.id.view_333)
    RecyclerView view333;
    @BindView(R.id.horizontal_scroll_view)
    HorizontalScrollView horizontalScrollView;
    private AppAdapter view111Adapter;
    private AppAdapter view222Adapter;
    private AppAdapter view333Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolll_conflict);
        ButterKnife.bind(this);
        initListener();
        initData();
    }


    private void initListener() {
        view111.setLayoutManager(new LinearLayoutManager(this));
        view111Adapter = new AppAdapter(R.layout.item_app_adapter, null);
        view111.setAdapter(view111Adapter);

        view222.setLayoutManager(new LinearLayoutManager(this));
        view222Adapter = new AppAdapter(R.layout.item_app_adapter, null);
        view222.setAdapter(view111Adapter);

        view333.setLayoutManager(new LinearLayoutManager(this));
        view333Adapter = new AppAdapter(R.layout.item_app_adapter, null);
        view333.setAdapter(view111Adapter);
    }


    private void initData() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add("aa"+i);
        }

        view111Adapter.setNewData(arrayList);
        view222Adapter.setNewData(arrayList);
        view333Adapter.setNewData(arrayList);

    }







}
