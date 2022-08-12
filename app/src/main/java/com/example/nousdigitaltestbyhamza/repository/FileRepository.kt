package com.example.nousdigitaltestbyhamza.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.nousdigitaltestbyhamza.Resource
import com.example.nousdigitaltestbyhamza.Utils
import com.example.nousdigitaltestbyhamza.model.Post
import com.example.nousdigitaltestbyhamza.network.ApiInterface
import com.example.nousdigitaltestbyhamza.roomDB.PostDataBase
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


class FileRepository @Inject constructor(
    private val api: ApiInterface,
    @ApplicationContext private val appContext: Context,
    private val postDb: PostDataBase
) {
    private val TAG = "FileRepose"
    suspend fun downloadJsonFile(url: String): Resource<List<Post>> {
        try {
            val reponse = api.downloadJsonFile(url)
            if (reponse.isSuccessful) {
                val byteArray = reponse.body()?.bytes()
                if (byteArray != null) {
                    val jsonString = String(byteArray)
                    val list = parsePosts(jsonString)
                    postDb.postDao().insertAll(list)
                    return Resource.Success(list)
                } else {
                    return Resource.Error(null, -1, "we don't find any data")
                }
            } else {
                return Resource.Error(
                    null,
                    reponse.code(),
                    Utils.parseErrorBody(reponse.errorBody()?.string())
                )
            }
        } catch (jsonEx: JSONException) {
            jsonEx.printStackTrace()
            return Resource.Error(jsonEx, -1, jsonEx.message.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e, -1, e.message.toString())
        }
    }

    @Throws(JSONException::class)
    private fun parsePosts(jsonString: String): List<Post> {
        val list: ArrayList<Post> = ArrayList()
        val josonObjects = JSONObject(jsonString)
        val jsonArray = josonObjects.getJSONArray("items")
        for (i in 0 until jsonArray.length()) {
            val postId = if (jsonArray.getJSONObject(i).has("id")) {
                jsonArray.getJSONObject(i).getInt("id")
            } else {
                -1
            }
            val postTitle = if (jsonArray.getJSONObject(i).has("title")) {
                jsonArray.getJSONObject(i).getString("title")
            } else {
                null
            }
            val postDescription = if (jsonArray.getJSONObject(i).has("description")) {
                jsonArray.getJSONObject(i).getString("description")
            } else {
                null
            }
            val postImageUrl = if (jsonArray.getJSONObject(i).has("imageUrl")) {
                jsonArray.getJSONObject(i).getString("imageUrl")
            } else {
                null
            }

            list.add(Post(postId, postTitle, postDescription, postImageUrl))
        }

        return list
    }

    fun downloadImage(url: String, onSuccess: (Bitmap) -> Unit, onFailer: (Exception) -> Unit) {
        Glide.with(appContext)
            .asBitmap()
            .load(url)
            .override(Target.SIZE_ORIGINAL)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    e?.let { onFailer(it) }
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    onSuccess(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }
}