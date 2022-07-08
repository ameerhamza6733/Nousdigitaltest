package com.example.nousdigitaltestbyhamza.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nousdigitaltestbyhamza.model.Post

class SharedViewModel: ViewModel() {
    public val dataPostListFragmentToDetailFragment:MutableLiveData<Post?> =MutableLiveData()
}