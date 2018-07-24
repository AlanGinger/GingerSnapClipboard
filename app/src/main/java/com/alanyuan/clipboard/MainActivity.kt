package com.alanyuan.clipboard

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val clipboardAdapter: ClipboardAdapter = ClipboardAdapter()
    private var mRealm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_paster.layoutManager = LinearLayoutManager(this)
        rv_paster.adapter = clipboardAdapter
        startService()
        initData()

//        clipboardAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
//            val clipText = clipboardAdapter.data.get(position).clipText
//            val clipData = ClipData.newPlainText("text", clipText)
//            val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            clipboard.primaryClip = clipData
//            toast("已复制:$clipText")
//        }
    }

    private fun initData() {
        try {
            mRealm = Realm.getInstance(RealmHelper.RealmConfig)
            val mRealmResults = mRealm?.where<ClipboardData>()?.findAllAsync()
            mRealmResults?.addChangeListener { results: RealmResults<ClipboardData> ->
                val clipboardDataList: List<ClipboardData>? = mRealm?.copyFromRealm(results)
                clipboardAdapter.setData(clipboardDataList)
            }
        } catch (e: Exception) {
        }
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, ClipboardService::class.java))
        } else {
            startService(Intent(this, ClipboardService::class.java))
        }
    }


}
