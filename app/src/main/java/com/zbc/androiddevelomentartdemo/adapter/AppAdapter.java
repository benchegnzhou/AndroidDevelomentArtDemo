package com.zbc.androiddevelomentartdemo.adapter;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;
/**
 * Created by benchengzhou on 2019/4/19  13:58 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： AppAdapter
 * 备    注：
 */

public class AppAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public AppAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
