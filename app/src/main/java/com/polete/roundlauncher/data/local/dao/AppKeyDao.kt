package com.polete.roundlauncher.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.polete.roundlauncher.data.local.entity.AppKey
import kotlinx.coroutines.flow.Flow

@Dao
interface AppKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: AppKey)

    @Query(
        """
            SELECT `key` FROM appkey
        """
    )
    fun getAll(): Flow<List<String>>

    @Delete
    suspend fun delete(key: AppKey)
}