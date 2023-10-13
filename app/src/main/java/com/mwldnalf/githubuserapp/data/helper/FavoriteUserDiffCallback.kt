package com.mwldnalf.githubuserapp.data.helper

import androidx.recyclerview.widget.DiffUtil
import com.mwldnalf.githubuserapp.data.database.FavoriteUser

class FavoriteUserDiffCallback(private val mOldFavoriteListUser: List<FavoriteUser>, private val mNewFavoriteListUser: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize() : Int = mOldFavoriteListUser.size
    override fun getNewListSize() : Int = mNewFavoriteListUser.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteListUser[oldItemPosition].id == mNewFavoriteListUser[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = mOldFavoriteListUser[oldItemPosition]
        val newFavoriteUser = mNewFavoriteListUser[newItemPosition]
        return oldFavoriteUser.username == newFavoriteUser.username && oldFavoriteUser.avatarUrl == newFavoriteUser.avatarUrl
    }
}