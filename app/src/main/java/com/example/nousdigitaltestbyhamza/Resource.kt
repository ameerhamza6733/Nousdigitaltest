package com.example.nousdigitaltestbyhamza

sealed class Resource <T>{
    data class Success<T>(val data:T,val message:String=""):Resource<T>()
    data class Error<T>(val error:Throwable?,val errorCode:Int=-1,var errorMessage:String=""):Resource<T>()
    data class Loading<T>(val message:String=""):Resource<T>()
}

