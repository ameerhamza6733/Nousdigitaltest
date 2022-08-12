package com.example.nousdigitaltestbyhamza.di

import android.content.Context
import com.example.nousdigitaltestbyhamza.R
import com.example.nousdigitaltestbyhamza.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiProviderModel {
    @Singleton
    @Provides
    fun createApi(@ApplicationContext appContext: Context): ApiInterface {
        val jsonFileUrl = appContext.getString(R.string.baseUrl)
        return Retrofit.Builder().baseUrl(jsonFileUrl).build().create(ApiInterface::class.java)
    }
}