package com.example.nousdigitaltestbyhamza.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.nousdigitaltestbyhamza.model.Post

@Dao
interface PostDao {

    @Insert(onConflict = REPLACE)
    fun insertAll( post: List<Post>)

    @Query("SELECT * FROM post WHERE title LIKE '%' || :value  || '%'")
    fun filter(value: String): List<Post>

    @Query("SELECT * FROM post WHERE postDescription LIKE '%' || :value  || '%'")
    fun filterByDes(value: String): List<Post>

    @Query("SELECT * FROM post ")
    fun getAllPost(): List<Post>


}