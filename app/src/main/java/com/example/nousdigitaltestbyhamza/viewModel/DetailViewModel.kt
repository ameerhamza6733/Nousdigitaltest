package com.example.nousdigitaltestbyhamza.viewModel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider.getUriForFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nousdigitaltestbyhamza.FileUtils
import com.example.nousdigitaltestbyhamza.FileUtils.Companion.writerToFile
import com.example.nousdigitaltestbyhamza.Log
import com.example.nousdigitaltestbyhamza.Resource
import com.example.nousdigitaltestbyhamza.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val fileRepository: FileRepository
) :
    ViewModel() {

    private val mutableLiveData: MutableLiveData<Resource<Uri>> = MutableLiveData()
    val liveDataEmailImage: LiveData<Resource<Uri>> = mutableLiveData

    fun preparTheImageForEmail(url: String) {
        mutableLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {

            fileRepository.downloadImage(url, {

                try {
                    val newFile = FileUtils.createImageFile(applicationContext = applicationContext)
                    it.writerToFile(file = newFile)
                    val contentUri: Uri = getUriForFile(
                        applicationContext,
                        "com.example.nousdigitaltestbyhamza.fileprovider",
                        newFile
                    )
                    Log("uri ${contentUri.toString()}")
                    mutableLiveData.postValue(Resource.Success(contentUri))

                }catch (E:Exception){
                    mutableLiveData.postValue(Resource.Error(E, -1, E.message.toString()))

                }
            }, {
                it.printStackTrace()
                mutableLiveData.postValue(Resource.Error(it, -1, it.message.toString()))
            })
        }

    }
}