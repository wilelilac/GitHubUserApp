package com.mwldnalf.githubuserapp.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mwldnalf.githubuserapp.adapter.FavoriteUserAdapter
import com.mwldnalf.githubuserapp.data.helper.ViewModelFactory
import com.mwldnalf.githubuserapp.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteUserAdapter
    private lateinit var userFavoriteViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userFavoriteViewModel = obtainViewModel(this)

        adapter = FavoriteUserAdapter(this)

        userFavoriteViewModel.getAllUsers().observe(this){
            if(it != null){
                adapter.setListFavoriteUser(it)
            }
        }
        binding.rvFavoritUser.layoutManager = LinearLayoutManager(this)
        binding.rvFavoritUser.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }
}
