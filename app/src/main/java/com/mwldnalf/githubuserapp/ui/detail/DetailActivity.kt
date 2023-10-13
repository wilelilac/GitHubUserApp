package com.mwldnalf.githubuserapp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mwldnalf.githubuserapp.R
import com.mwldnalf.githubuserapp.adapter.SectionsPagerAdapter
import com.mwldnalf.githubuserapp.data.database.FavoriteUser
import com.mwldnalf.githubuserapp.data.helper.ViewModelFactory
import com.mwldnalf.githubuserapp.databinding.ActivityDetailBinding
import com.mwldnalf.githubuserapp.ui.favorite.FavoriteUserViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel
    private val detailViewModel by viewModels<DetailViewModel>()
    private var isFavoriteUser: Boolean = false
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        favoriteUserViewModel = obtainViewModel(this)
        username = intent.getStringExtra("USER_EXTRA")
        showUserData(username.toString())
        detailViewModel.isProgressDetail.observe(this) { showLoading(it) }
        favoriteUserViewModel.isFavoriteUser(username.toString()).observe(this){
            if(it){
                isFavoriteUser = true
                binding.floatingActionButton.setImageResource(R.drawable.myfavorite_icon)
            }
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        if (username != null) sectionsPagerAdapter.username = username as String
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        detailViewModel.message.observe(this) {
            it.getContentIfNotHandled()?.let {
                    text -> Toast.makeText(this, text, Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detailViewModel.isProgressDetail.removeObservers(this)
        favoriteUserViewModel.isFavoriteUser(username.toString()).removeObservers(this)
        detailViewModel.message.removeObservers(this)
    }

    @SuppressLint("SetTextI18n")
    private fun showUserData(username: String) {
        detailViewModel.getUser(username)
        detailViewModel.user.observe(this) { user ->
            with(binding) {
                Glide.with(this@DetailActivity).load(user.avatarUrl).into(photoDetail)
                nameDetail.text = user.name
                usernameDetail.text = user.login
                tvFollowersDetail.text = getString(R.string.followertotal, user.followers)
                tvFollowingDetail.text = getString(R.string.followingtotal, user.following)

                floatingActionButton.setOnClickListener {
                    isFavoriteUser = !isFavoriteUser
                    if (isFavoriteUser) {
                        favoriteUserViewModel.insertUser(
                            FavoriteUser(user.id, user.login, user.avatarUrl)
                        )
                        floatingActionButton.setImageResource(R.drawable.myfavorite_icon)
                    } else {
                        favoriteUserViewModel.deleteUser(
                            FavoriteUser(user.id, user.login, user.avatarUrl)
                        )
                        floatingActionButton.setImageResource(R.drawable.myfavorite_border_icon)
                    }
                }

                share.setOnClickListener {
                    val shareUser: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Visit GitHub $username For Latest Source Code!\n " +
                                    "GitHub Profile Link : https://github.com/$username"
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(shareUser, null)
                    startActivity(shareIntent)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            tvFollowersDetail.visibility = if (isLoading) View.GONE else View.VISIBLE
            tvFollowingDetail.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)
    }
}