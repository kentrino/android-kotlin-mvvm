package jp.furyu.mvvm_test.viewmodel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import jp.furyu.mvvm_test.Project
import jp.furyu.mvvm_test.ProjectRepository
import javax.inject.Inject

class ProjectListViewModel: ViewModel() {
    // LiveDataはAbstractClassで初期化できないとの警告を得た
    val projectList = MutableLiveData<List<Project>>()
    private lateinit var repository: ProjectRepository

    @Inject fun init(repository: ProjectRepository) {
        this.repository = repository
    }
}
