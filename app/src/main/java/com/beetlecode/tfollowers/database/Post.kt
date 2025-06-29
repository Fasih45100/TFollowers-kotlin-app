package com.beetlecode.tfollowers.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val dpImagUri: String?,
    @ColumnInfo val firstName: String?,
    @ColumnInfo val handlername: String?,
    @ColumnInfo val posttext: String?,
    @ColumnInfo val postimage: String?,
    @ColumnInfo val isVerified: Boolean,

    @ColumnInfo val comment: String?,
    @ColumnInfo val retweet: String?,
    @ColumnInfo val like: String?,
    @ColumnInfo val viewtweet: String?,


    )