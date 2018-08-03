package jp.furyu.mvvm_test.viewmodel

import android.arch.lifecycle.*
import android.support.v4.app.FragmentActivity
import jp.furyu.mvvm_test.Project
import jp.furyu.mvvm_test.repository.ProjectRepository
import jp.furyu.mvvm_test.Resource
import javax.inject.Inject

class ProjectListViewModel: ViewModel() {
    private val userNameInput = MutableLiveData<String>()

    // LiveDataはAbstractClassで初期化できないとの警告を得た
    // val projectList = MutableLiveData<List<Project>>()
    val projectList: LiveData<Resource<List<Project>>> = Transformations.switchMap(userNameInput) {
        if (it.length >= 1) {
            repository.getProjectList(it)
        } else {
            MutableLiveData()
        }
    }

    private lateinit var repository: ProjectRepository

    @Inject fun init(repository: ProjectRepository) {
        this.repository = repository
    }

    // TODO: MutableLiveDataになっている時点でこれが入力である可能性が示唆されているので
    // このような隠蔽は必要ない可能性がある
    fun getProjectList(userName: String) {
        userNameInput.value = userName
    }

    companion object {
        fun create(activity: FragmentActivity): ProjectListViewModel {
            // これはなぜactivityを引数にとっているのだろう
            val projectListViewModel = ViewModelProviders.of(activity).get(ProjectListViewModel::class.java)
            return projectListViewModel
        }
    }
}
