package com.vcs.data.base

import java.time.LocalDateTime

interface RetireItemBase2 {
    var type: Byte
    var freq: Byte
    var startDateTime: LocalDateTime
}