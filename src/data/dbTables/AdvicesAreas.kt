@file:Suppress("MemberVisibilityCanBePrivate")

package com.vcs.data.dbTables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object AdvicesAreas: Table() {
    val area = reference("area", Areas2)
    val advice = reference("advice", Advices, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(area, advice)
}