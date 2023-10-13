package com.mwldnalf.githubuserapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mwldnalf.githubuserapp.ui.detail.FollowUserFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username = ""

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment = FollowUserFragment().apply {
        arguments = Bundle().apply {
            putInt(FollowUserFragment.ARG_POSITION, position + 1)
            putString(FollowUserFragment.ARG_USERNAME, username)
        }
    }
}

