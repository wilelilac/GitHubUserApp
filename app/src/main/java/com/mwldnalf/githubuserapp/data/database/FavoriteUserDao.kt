package com.mwldnalf.githubuserapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: FavoriteUser)

    @Delete
    fun deleteUser(user: FavoriteUser)

    @Query("SELECT * FROM favorite ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM favorite WHERE username = :username)")
    fun isFavoritUser(username: String): LiveData<Boolean>
}