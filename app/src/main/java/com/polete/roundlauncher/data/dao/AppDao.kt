//package com.polete.roundlauncher.data.dao
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Update
//import com.polete.roundlauncher.data.entities.AppEntity
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface AppDao {
//
//    @Insert
//    suspend fun insert(app: AppEntity)
//
//    @Update
//    suspend fun update(app: AppEntity)
//
//    @Delete
//    suspend fun delete(app: AppEntity)
//
//    @Query("DELETE FROM app WHERE pkgname = :pkgname")
//    suspend fun deleteByPKG(pkgname: String)
//
//    @Query("SELECT * FROM app")
//    fun getAllApps(): Flow<List<AppEntity>>
//
//
//}