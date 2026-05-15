package com.polete.roundlauncher.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppKey(
    @PrimaryKey
    val key: String
)