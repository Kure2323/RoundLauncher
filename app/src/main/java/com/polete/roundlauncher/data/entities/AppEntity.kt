package com.polete.roundlauncher.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "app"
)
data class AppEntity (
    @PrimaryKey
    val pkgname: String,
    val userSerialNumber: Long
)