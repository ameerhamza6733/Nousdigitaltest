package com.example.nousdigitaltestbyhamza.repository

import android.content.Context
import com.example.nousdigitaltestbyhamza.model.Post
import com.example.nousdigitaltestbyhamza.network.ApiInterface
import com.example.nousdigitaltestbyhamza.roomDB.PostDataBase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PostSearchRepo @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val postDb: PostDataBase
) {

    suspend fun searchByTitle(txt:String): List<Post> {
       return postDb.postDao().fillter(txt)
    }

    suspend fun getAllData():List<Post>{
        return postDb.postDao().getAllPost()
    }

    suspend fun searchByDescription(txt:String): List<Post> {
       return postDb.postDao().fillterByDes(txt)
    }
}