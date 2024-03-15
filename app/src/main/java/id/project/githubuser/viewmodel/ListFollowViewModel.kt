package id.project.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.project.githubuser.data.api.ApiConfig
import id.project.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFollowViewModel : ViewModel() {
    companion object {
        private const val TAG = "ListFollowViewModel"
    }

    private val _githubUserListFollower = MutableLiveData<List<ItemsItem>>()
    val githubUserListFollower: LiveData<List<ItemsItem>> = _githubUserListFollower

    private val _githubUserListFollowing = MutableLiveData<List<ItemsItem>>()
    val githubUserListFollowing: LiveData<List<ItemsItem>> = _githubUserListFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getGithubUserListFollower(userName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersFollowers(userName)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUserListFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getGithubUserListFollowing(userName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersFollowing(userName)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUserListFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}