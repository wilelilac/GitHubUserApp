package com.mwldnalf.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwldnalf.githubuserapp.util.Event
import com.mwldnalf.githubuserapp.data.response.DetailUserResponse
import com.mwldnalf.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val userDetail = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> get() = userDetail

    private val userFollowDetail = MutableLiveData<List<DetailUserResponse>>()
    val userFollow: LiveData<List<DetailUserResponse>> get() = userFollowDetail

    private val isProgress = MutableLiveData<Boolean>()
    val isProgressDetail: LiveData<Boolean> get() = isProgress

    private val isMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = isMessage

    fun getUser(username: String) {
        isProgress.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                isProgress.value = false
                if (response.isSuccessful) {
                    userDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                isProgress.value = false
                isMessage.value = Event(t.message.toString())
            }
        })
    }

    private fun getUserFollow(username: String, follower: Boolean) {
        isProgress.value = true
        val service = if (follower) ApiConfig.getApiService().getUserFollowers(username)
        else ApiConfig.getApiService().getUserFollowing(username)

        service.enqueue(object : Callback<List<DetailUserResponse>> {
            override fun onResponse(
                call: Call<List<DetailUserResponse>>,
                response: Response<List<DetailUserResponse>>
            ) {
                isProgress.value = false
                if (response.isSuccessful) {
                    userFollowDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<DetailUserResponse>>, t: Throwable) {
                isProgress.value = false
                isMessage.value = Event(t.message.toString())
            }
        })
    }

    fun getUserFollower(username: String) {
        getUserFollow(username, follower = true)
    }

    fun getUserFollowing(username: String) {
        getUserFollow(username, follower = false)
    }
}
