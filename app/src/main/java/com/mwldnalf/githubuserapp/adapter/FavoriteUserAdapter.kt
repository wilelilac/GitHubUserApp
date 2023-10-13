package com.mwldnalf.githubuserapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mwldnalf.githubuserapp.data.database.FavoriteUser
import com.mwldnalf.githubuserapp.data.helper.FavoriteUserDiffCallback
import com.mwldnalf.githubuserapp.databinding.ItemRowFavoriteUserBinding
import com.mwldnalf.githubuserapp.ui.detail.DetailActivity

class FavoriteUserAdapter(private val context: Context) :
    RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {

    private val listFavoriteUser = ArrayList<FavoriteUser>()

    inner class FavoriteUserViewHolder(private val binding: ItemRowFavoriteUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.usernameFavorite.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val favoriteUser = listFavoriteUser[position]
                    navigateToDetail(favoriteUser.username)
                }
            }
        }

        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                Glide.with(context).load(favoriteUser.avatarUrl).into(photoFavorit)
                usernameFavorite.text = favoriteUser.username
            }
        }

        private fun navigateToDetail(username: String?) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("USER_EXTRA", username)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding =
            ItemRowFavoriteUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun getItemCount() = listFavoriteUser.size

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.bind(listFavoriteUser[position])
    }

    fun setListFavoriteUser(listFavoriteUser: List<FavoriteUser>) {
        val diffCallback = FavoriteUserDiffCallback(this.listFavoriteUser, listFavoriteUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUser.clear()
        this.listFavoriteUser.addAll(listFavoriteUser)
        diffResult.dispatchUpdatesTo(this)
    }
}
