package com.polete.roundlauncher.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.polete.roundlauncher.data.UApp
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert
    suspend fun insert(app: UApp)

    @Update
    suspend fun update(app: UApp)

    @Delete
    suspend fun delete(app: UApp)

    @Query("DELETE FROM app WHERE pkgname = :pkgname")
    suspend fun deleteByPKG(pkgname: String)

    @Query("SELECT * FROM app")
    fun getAllApps(): Flow<List<UApp>>


}