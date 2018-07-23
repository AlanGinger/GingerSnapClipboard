package com.alanyuan.clipboard

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alanyuan.clipboard.databinding.ItemClipboardBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ClipboardAdapter : BaseQuickAdapter<ClipboardData, ClipboardAdapter.ClipboardViewHolder>(R.layout.item_clipboard) {

    override fun createBaseViewHolder(view: View?): ClipboardViewHolder {
        return super.createBaseViewHolder(view)
    }

    override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): ClipboardViewHolder {
        val binding: ItemClipboardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_clipboard, parent, false)
        ClipboardViewHolder(binding).binding.clip
        return createBaseViewHolder(parent, layoutResId)
    }

    override fun convert(helper: ClipboardViewHolder, item: ClipboardData) {
        helper.setText(R.id.tv_clip, item.clipText)
    }

    class ClipboardViewHolder(val binding: ItemClipboardBinding) : BaseViewHolder(binding.root)
}