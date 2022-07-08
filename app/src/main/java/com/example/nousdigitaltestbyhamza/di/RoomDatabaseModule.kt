package com.example.nousdigitaltestbyhamza.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.nousdigitaltestbyhamza.model.Post
import com.example.nousdigitaltestbyhamza.roomDB.PostDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {
    @Singleton
    @Provides
    fun getDatabase( @ApplicationContext app: Context):PostDataBase{
      return  Room.databaseBuilder(
            app,
          PostDataBase::class.java,
            "database"
        ).build()
    }

}