package com.mwldnalf.githubuserapp.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mwldnalf.githubuserapp.R
import com.mwldnalf.githubuserapp.adapter.DetailAdapter
import com.mwldnalf.githubuserapp.data.response.DetailUserResponse
import com.mwldnalf.githubuserapp.databinding.ActivityMainBinding
import com.mwldnalf.githubuserapp.ui.detail.DetailActivity
import com.mwldnalf.githubuserapp.ui.favorite.FavoriteUserActivity
import com.mwldnalf.githubuserapp.ui.setting.SettingActivity
import com.mwldnalf.githubuserapp.ui.setting.SettingPreference
import com.mwldnalf.githubuserapp.ui.setting.SettingViewModel
import com.mwldnalf.githubuserapp.ui.setting.SettingViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preference = SettingPreference.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, SettingViewModelFactory(preference))[SettingViewModel::class.java]
        settingsViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchBar.inflateMenu(R.menu.main_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId){
                    R.id.favorit ->{
                        startActivity(Intent(this@MainActivity, FavoriteUserActivity::class.java))
                        true
                    }
                    R.id.setting ->{
                        startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    viewModel.getUsers(searchView.text.toString())
                    false
                }
        }
        getData()
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        viewModel.message.observe(this) {
            it.getContentIfNotHandled()?.let { text -> Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }
        }
        viewModel.progress.observe(this) {
            showLoading(it)
        }
    }

    private fun getData() {
        viewModel.users.observe(this) { userList ->
            val adapter = DetailAdapter(userList, object : DetailAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DetailUserResponse) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra("USER_EXTRA", data.login)
                    startActivity(intent)
                }
            })
            binding.rvUser.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvUser.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}