package id.project.githubuser.data.api

import id.project.githubuser.data.response.DetailGithubUserResponse
import id.project.githubuser.data.response.GithubResponse
import id.project.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListUsers(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailGithubUserResponse>

    @GET("users/{username}/followers")
    fun getUsersFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getUsersFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}