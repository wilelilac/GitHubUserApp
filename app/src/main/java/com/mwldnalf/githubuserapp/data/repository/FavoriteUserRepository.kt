package com.mwldnalf.githubuserapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mwldnalf.githubuserapp.data.database.FavoriteUser
import com.mwldnalf.githubuserapp.data.database.FavoriteUserDao
import com.mwldnalf.githubuserapp.data.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllUsers()

    fun insertUser(user: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insertUser(user) }
    }

    fun deleteUser(user: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.deleteUser(user) }
    }

    fun isFavoriteUser(username: String): LiveData<Boolean> = mFavoriteUserDao.isFavoritUser(username)
}