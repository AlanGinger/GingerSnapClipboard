package com.alanyuan.clipboard

import io.realm.RealmConfiguration

class RealmHelper {
    companion object {
        val RealmConfig: RealmConfiguration = RealmConfiguration.Builder()
                .name("clipboard.realm")
                .build()
    }
}