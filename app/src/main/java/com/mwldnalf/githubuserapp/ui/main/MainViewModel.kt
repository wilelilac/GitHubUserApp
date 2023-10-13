package com.mwldnalf.githubuserapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwldnalf.githubuserapp.util.Event
import com.mwldnalf.githubuserapp.data.response.DetailUserResponse
import com.mwldnalf.githubuserapp.data.response.GitHubResponse
import com.mwldnalf.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val dataUser = MutableLiveData<List<DetailUserResponse>>()
    val users: LiveData<List<DetailUserResponse>> = dataUser

    private val isProgress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = isProgress

    private val isMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = isMessage

    init {
        getUsers()
    }

    fun getUsers(username: String = "arif") {
        isProgress.value = true
        val client = ApiConfig.getApiService().getListUser(username)
        client.enqueue(object : Callback<GitHubResponse> {
            override fun onResponse(call: Call<GitHubResponse>, response: Response<GitHubResponse>) {
                isProgress.value = false
                if (response.isSuccessful) {
                    dataUser.value = response.body()?.items

                    if (dataUser.value?.isEmpty() == true) {
                        isMessage.value = Event("Can't Find User!!!")
                    }
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                isProgress.value = false
            }
        })
    }
}
