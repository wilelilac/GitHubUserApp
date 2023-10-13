package com.mwldnalf.githubuserapp.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mwldnalf.githubuserapp.R
import com.mwldnalf.githubuserapp.adapter.ListFollowUserAdapter
import com.mwldnalf.githubuserapp.data.response.DetailUserResponse
import com.mwldnalf.githubuserapp.databinding.FragmentUserFollowBinding

class FollowUserFragment : Fragment(R.layout.fragment_user_follow) {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: FragmentUserFollowBinding
    private var position: Int? = null
    private var username: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserFollowBinding.bind(view)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserFollow.layoutManager = layoutManager

        detailViewModel.isProgressDetail.observe(viewLifecycleOwner) { showLoading(it) }

        when (position) {
            1 -> detailViewModel.getUserFollower(username.toString())
            else -> detailViewModel.getUserFollowing(username.toString())
        }

        detailViewModel.userFollow.observe(viewLifecycleOwner) { users ->
            showFollowersOrFollowing(users)
        }
    }

    private fun showFollowersOrFollowing(users: List<DetailUserResponse>) {
        val listUserFollowAdapter = ListFollowUserAdapter(
            users,
            object : ListFollowUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DetailUserResponse) {
                    val intent = Intent(requireContext(), DetailActivity::class.java)
                    intent.putExtra("USER_EXTRA", data.login)
                    startActivity(intent)
                }
            })

        binding.rvUserFollow.adapter = listUserFollowAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION: String = "FollowUserFragmentPosition"
        const val ARG_USERNAME: String = "FollowUserFragmentUsername"
    }
}

