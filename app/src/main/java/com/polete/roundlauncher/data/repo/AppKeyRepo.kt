package com.polete.roundlauncher.data.repo

import com.polete.roundlauncher.data.local.dao.AppKeyDao
import com.polete.roundlauncher.data.local.entity.AppKey
import kotlinx.coroutines.flow.Flow

class AppKeyRepo(
    private val dao: AppKeyDao
) {
    fun getAll(): Flow<List<String>> {
        return dao.getAll()
    }

    suspend fun insert(key: AppKey) {
        dao.insert(key)
    }

    suspend fun delete(key: AppKey) {
        dao.delete(key)
    }
}