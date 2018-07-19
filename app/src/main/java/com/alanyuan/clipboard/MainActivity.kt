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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_paster.layoutManager = LinearLayoutManager(this)
        clipboardAdapter.bindToRecyclerView(rv_paster)
        startService()
        initData()
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
