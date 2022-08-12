package com.example.nousdigitaltestbyhamza.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nousdigitaltestbyhamza.model.Post

@Database(entities = [Post::class], version = 1)
abstract class PostDataBase : RoomDatabase() {
    abstract fun postDao(): PostDao
}