package com.vcs.tools

import io.ktor.util.date.*

object WeekDayConverter {

    fun fromStringTag(stringTag: String): WeekDay {
        return when(stringTag) {
            "mon" -> WeekDay.MONDAY
            "tue" -> WeekDay.TUESDAY
            "wed" -> WeekDay.WEDNESDAY
            "thu" -> WeekDay.THURSDAY
            "fri" -> WeekDay.FRIDAY
            "sat" -> WeekDay.SATURDAY
            else -> WeekDay.SUNDAY
        }
    }
}