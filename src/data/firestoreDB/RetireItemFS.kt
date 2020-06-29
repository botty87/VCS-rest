package com.vcs.data.firestoreDB

import com.google.cloud.Timestamp

data class RetireItemFS (
    val freq: Int = 0,
    val time: Int = 0,
    val type: String = "",
    var name: String = "",
    var startDate: Timestamp? = null
)