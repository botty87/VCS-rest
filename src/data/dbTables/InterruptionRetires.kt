package com.vcs.data.dbTables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object InterruptionRetires: Table() {
    val interruption = reference("interruption", Interruptions, onDelete = ReferenceOption.CASCADE)
    val retire = reference("retire", Retires2, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(interruption, retire)
}