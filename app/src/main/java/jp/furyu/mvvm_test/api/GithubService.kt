package jp.furyu.mvvm_test.api

import jp.furyu.mvvm_test.dto.Project
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{user}/repos")
    fun getProjectList(@Path("user") user: String): Call<List<Project>>

    @GET("/repos/{user}/{reponame}")
    fun getProjectDetails(@Path("user") user: String, @Path("reponame") peoject: String): Call<Project>

    companion object {
        val GITHUB_API_URL = "https://api.github.com"
    }
}
