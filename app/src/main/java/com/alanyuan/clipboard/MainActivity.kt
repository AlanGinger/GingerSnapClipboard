package com.alanyuan.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    private val clipboardAdapter: ClipboardAdapter = ClipboardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_paster.layoutManager = LinearLayoutManager(this)
        clipboardAdapter.bindToRecyclerView(rv_paster)
        startService()
        initData()

        clipboardAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val clipText = clipboardAdapter.data.get(position).clipText
            val clipData = ClipData.newPlainText("text", clipText)
            val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.primaryClip = clipData
            toast("已复制:$clipText")
        }
    }

    private fun initData() {
        val mRealm = Realm.getInstance(RealmHelper.RealmConfig)
        try {
            val mRealmResults = mRealm.where<ClipboardData>().findAllAsync()
            mRealmResults.addChangeListener { results: RealmResults<ClipboardData>? ->
                val clipboradDataList: List<ClipboardData> = mRealm.copyFromRealm(results)
                clipboardAdapter.setNewData(clipboradDataList)
            }
        } catch (e: Exception) {
        }
    }

    fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, ClipboardService::class.java))
        } else {
            startService(Intent(this, ClipboardService::class.java))
        }
    }


}
