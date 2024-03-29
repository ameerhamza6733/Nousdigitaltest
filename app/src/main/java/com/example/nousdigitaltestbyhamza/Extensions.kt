package com.example.nousdigitaltestbyhamza

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import org.json.JSONObject
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun Activity.Log(tag:String){
    android.util.Log.d(javaClass.simpleName,tag)
}

fun ViewModel.Log(tag: String){
    android.util.Log.d(javaClass.simpleName,tag)

}

fun Fragment.Log(tag: String){
    android.util.Log.d(javaClass.simpleName,tag)
}
//fun CoroutineWorker.Log(tag: String){
//    android.util.Log.d(javaClass.simpleName,tag)
//}fun Worker.Log(tag: String){
//    android.util.Log.d(javaClass.simpleName,tag)
//}



fun Throwable.isInternetError() =
    this is UnknownHostException || this is SocketTimeoutException


