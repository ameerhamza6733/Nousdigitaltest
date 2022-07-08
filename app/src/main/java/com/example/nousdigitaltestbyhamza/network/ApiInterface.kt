package com.example.nousdigitaltestbyhamza.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiInterface {
    @Streaming
    @GET
    suspend fun downloadJsonFile(@Url fileUrl:String): Response<ResponseBody>
}