package jp.furyu.mvvm_test

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import jp.furyu.mvvm_test.exception.AppException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProjectRepository {
    private var githubService: GithubService

    private constructor() {
        val retrofit = Retrofit.Builder()
                .baseUrl(GithubService.GITHUB_API_URL)
                // 他のサンプルではこうなっていた
                // https://medium.com/@elye.project/kotlin-and-retrofit-2-tutorial-with-working-codes-333a4422a890
                // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        githubService = retrofit.create(GithubService::class.java)
    }

    companion object {
        private var projectRepository: ProjectRepository? = null

        fun getInstance(): ProjectRepository {
            if (projectRepository == null) {
                projectRepository = ProjectRepository()
            }
            return projectRepository!!
        }
    }

    fun getProjectList(userId: String): LiveData<Resource<List<Project>>> {
        val data = MutableLiveData<Resource<List<Project>>>()
        // https://medium.com/@elye.project/kotlin-and-retrofit-2-tutorial-with-working-codes-333a4422a890
        githubService
                .getProjectList(userId)
                .enqueue(object: Callback<List<Project>> {
                    override fun onFailure(call: Call<List<Project>>?, t: Throwable?) {
                        val exception = AppException(t)
                        data.value = Resource.error( exception)
                    }

                    override fun onResponse(call: Call<List<Project>>?, response: Response<List<Project>>?) {
                        data.value = Resource.success(response?.body())
                    }
                })

        return data
    }

    fun getProjectDetails(userId: String, projectName: String) {
        val data = MutableLiveData<Resource<Project>>()

        githubService
                .getProjectDetails(userId, projectName)
                .enqueue(object: Callback<Project> {
                    override fun onFailure(call: Call<Project>?, t: Throwable?) {
                        val e = AppException(t)
                        data.value = Resource.error(e)
                    }

                    override fun onResponse(call: Call<Project>?, response: Response<Project>?) {
                        data.value = Resource.success(response?.body())
                    }
                })
    }
}
