package com.example.activityTransitiondemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.activityTransitiondemo.R

class HomeListAdapter(layoutResId: Int, data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

    init {
//        addChildClickViewIds()
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_title, item)
    }


}