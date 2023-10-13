package com.mwldnalf.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mwldnalf.githubuserapp.data.response.DetailUserResponse
import com.mwldnalf.githubuserapp.databinding.ItemRowUserFollowBinding

class ListFollowUserAdapter(
    private val listUserFollow: List<DetailUserResponse>,
    private val onItemClickCallback: OnItemClickCallback
) : RecyclerView.Adapter<ListFollowUserAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemRowUserFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DetailUserResponse) {
            with(binding) {
                Glide.with(itemView.context).load(data.avatarUrl).into(photoFollow)
                usernameFollow.text = data.login
                cvFollow.setOnClickListener {
                    onItemClickCallback.onItemClicked(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserFollowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount() = listUserFollow.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listUserFollow[position]
        holder.bind(data)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailUserResponse)
    }
}
