package com.alanyuan.clipboard

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ClipboardData : RealmObject() {

    @PrimaryKey
    var clipTimeMillis: Long = 0

    var clipText: String = ""

    var isHtml: Boolean = false
}