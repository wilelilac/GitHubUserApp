package com.mwldnalf.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mwldnalf.githubuserapp.data.database.FavoriteUser
import com.mwldnalf.githubuserapp.data.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllUsers(): LiveData<List<FavoriteUser>> {
        return favoriteUserRepository.getAllUsers()
    }

    fun insertUser(user: FavoriteUser) {
        favoriteUserRepository.insertUser(user)
    }

    fun deleteUser(user: FavoriteUser) {
        favoriteUserRepository.deleteUser(user)
    }

    fun isFavoriteUser(username: String): LiveData<Boolean> {
        return favoriteUserRepository.isFavoriteUser(username)
    }
}