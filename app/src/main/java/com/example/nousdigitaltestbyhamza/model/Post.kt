package com.example.nousdigitaltestbyhamza.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val postDescription: String?,
    val postImageUrl: String?
)