package id.project.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.project.githubuser.data.api.ApiConfig
import id.project.githubuser.data.response.DetailGithubUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _githubUserDetail = MutableLiveData<DetailGithubUserResponse>()
    val githubUserDetail: LiveData<DetailGithubUserResponse> = _githubUserDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoading

    fun getGithubUserDetail(userName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(userName)
        client.enqueue(object : Callback<DetailGithubUserResponse> {
            override fun onResponse(
                call: Call<DetailGithubUserResponse>,
                response: Response<DetailGithubUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubUserDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailGithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}