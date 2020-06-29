package com.vcs.data.firestoreDB

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap

data class AreaItemFS (
        var id: Int = 0,
        var name: String = "",
        var towns: List<String>? = null,
        val calendarMap: Multimap<String, String> = ArrayListMultimap.create()
)