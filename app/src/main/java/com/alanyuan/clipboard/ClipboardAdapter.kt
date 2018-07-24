package com.alanyuan.clipboard

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alanyuan.clipboard.databinding.ItemClipboardBinding

class ClipboardAdapter : RecyclerView.Adapter<ClipboardAdapter.ClipboardViewHolder>() {
    private lateinit var mContext: Context
    var mClipboardDataList: List<ClipboardData>? = null

    fun setData(dataList: List<ClipboardData>?) {
        this.mClipboardDataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClipboardViewHolder {
        mContext = parent.context
        val binding: ItemClipboardBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_clipboard, parent, false)
        return ClipboardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mClipboardDataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ClipboardViewHolder, position: Int) {
        holder.binding.clipData = mClipboardDataList?.get(position)
        holder.binding.executePendingBindings()
    }

    class ClipboardViewHolder(val binding: ItemClipboardBinding) : RecyclerView.ViewHolder(binding.root)
}