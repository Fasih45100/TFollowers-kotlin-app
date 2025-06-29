package com.beetlecode.tfollowers.database

class MyRepository (private val dao: MyDao){


    suspend fun insert(post: Post) = dao.insert(post)
    suspend fun delete(post: Post) = dao.delete(post)
    fun getAllPost() = dao.getAllPost()


}
