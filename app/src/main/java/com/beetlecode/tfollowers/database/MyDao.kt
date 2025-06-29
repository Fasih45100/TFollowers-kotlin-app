package com.beetlecode.tfollowers.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: Post)

    @Delete
    suspend fun delete(post: Post)

    @Query("SELECT * FROM post")
    fun getAllPost(): LiveData<List<Post>>


//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMedia(media: MMedia)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCached(cachedNotification: CachedNotification)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertWatchText(watcherText: WatcherText)
//
//    @Delete
//    suspend fun deleteCachedNotification(cachedNotification: CachedNotification)
//

//
//    @Delete
//    suspend fun deleteChat(chat: MMessage)
//
//    @Delete
//    suspend fun deleteMedia(media: MMedia)
//
//    @Query("SELECT * FROM message_table WHERE name = :name")
//    fun getAllMessagesByName(name: String): Flow<List<MMessage>>
//
//    @Query("SELECT * FROM media_table")
//    fun getAllMedia(): Flow<List<MMedia>>
//
//    @Query("SELECT * FROM message_table")
//    fun getAllChat(): Flow<List<MMessage>>
//
//    @Query("SELECT * FROM watch_table")
//    fun getAllWatcher(): Flow<List<WatcherText>>
//
//    @Query("SELECT * FROM cached_table")
//    fun getAllCached(): Flow<List<CachedNotification>>
}