package com.example.nousdigitaltestbyhamza.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nousdigitaltestbyhamza.Log
import com.example.nousdigitaltestbyhamza.R
import com.example.nousdigitaltestbyhamza.Resource
import com.example.nousdigitaltestbyhamza.model.Post
import com.example.nousdigitaltestbyhamza.repository.FileRepository
import com.example.nousdigitaltestbyhamza.repository.PostSearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListFragmentViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val fileRepository: FileRepository,
    private val postSearchRepo: PostSearchRepo
) : ViewModel() {

    private val _mutableLiveData: MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val postLiveData: LiveData<Resource<List<Post>>> = _mutableLiveData

    //ui data
    var postListItemClick: Int = -1
    var searchText: String? = ""

    init {
        downloadFileFromNetwork()
    }

    fun downloadFileFromNetwork() {
        Log("downloadFileFromNetwork()")
        _mutableLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            val downloadingFileUrl = appContext.getString(R.string.jsonFileUrl)
            val reponse = fileRepository.downloadJsonFile(downloadingFileUrl)
            _mutableLiveData.postValue(reponse)
        }
    }

    fun search(text: String?) {
        Log("search()")
        viewModelScope.launch(Dispatchers.IO) {
            if (text?.isNotEmpty() == true) {
                searchText = text
                _mutableLiveData.postValue(
                    Resource.Success(
                        postSearchRepo.searchByTitle(text),
                        "title"
                    )
                )
                _mutableLiveData.postValue(
                    Resource.Success(
                        postSearchRepo.searchByDescription(text),
                        "des"
                    )
                )
            } else {
                searchText = ""
                _mutableLiveData.postValue(
                    Resource.Success(
                        postSearchRepo.getAllData(),
                        "all date"
                    )
                )
            }
        }
    }

}
