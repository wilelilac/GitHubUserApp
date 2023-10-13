package com.mwldnalf.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mwldnalf.githubuserapp.data.response.DetailUserResponse
import com.mwldnalf.githubuserapp.databinding.ItemRowUserBinding

class DetailAdapter(
    private val listData: List<DetailUserResponse>,
    private val onItemClickCallback: OnItemClickCallback
) : RecyclerView.Adapter<DetailAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cvUser.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val data = listData[position]
                    onItemClickCallback.onItemClicked(data)
                }
            }
        }

        fun bind(data: DetailUserResponse) {
            with(binding) {
                Glide.with(itemView.context).load(data.avatarUrl).into(ivUser)
                tvUser.text = data.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailUserResponse)
    }
}
