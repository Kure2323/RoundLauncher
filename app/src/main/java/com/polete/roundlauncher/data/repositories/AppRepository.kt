//package com.polete.roundlauncher.data.repositories
//
//import com.polete.roundlauncher.data.dao.AppDao
//import com.polete.roundlauncher.data.entities.AppEntity
//import kotlinx.coroutines.flow.Flow
//
//class AppRepository(private val appDao: AppDao) {
//
//    suspend fun insert(app: AppEntity) {
//        appDao.insert(app)
//    }
//
//    suspend fun update(app: AppEntity) {
//        appDao.update(app)
//    }
//    suspend fun delete(app: AppEntity) {
//        appDao.insert(app)
//    }
//
//    fun getAllApps(): Flow<List<AppEntity>> {
//        return appDao.getAllApps()
//    }
//
//    suspend fun deleteByPKG(pkg: String) {
//        appDao.deleteByPKG(pkg)
//    }
//
//}