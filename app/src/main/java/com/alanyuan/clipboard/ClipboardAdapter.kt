package com.alanyuan.clipboard

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ClipboardAdapter() : BaseQuickAdapter<ClipboardData, BaseViewHolder>(R.layout.item_clipboard) {

    override fun convert(helper: BaseViewHolder, item: ClipboardData) {
        helper.setText(R.id.tv_clip, item.clipText)
    }
}