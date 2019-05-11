package com.unsplash.photo.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.unsplash.photo.model.PhotoResponse

@Dao
abstract class PhotoDao : BaseDao<PhotoResponse>() {
    @Query("SELECT * FROM ${PhotoResponse.TABLE_NAME_PHOTO} WHERE type = :type ORDER BY `indexInResponse` ASC")
    abstract fun getPhotos(type: Int): DataSource.Factory<Int, PhotoResponse>

    @Query("SELECT * FROM ${PhotoResponse.TABLE_NAME_PHOTO} ORDER BY `indexInResponse` DESC")
    abstract fun getReceivedEventAsLiveData(): DataSource.Factory<Int, PhotoResponse>

    @Query("DELETE FROM ${PhotoResponse.TABLE_NAME_PHOTO} WHERE type = :type")
    abstract fun deleteAll(type: Int)

    @Query("DELETE FROM ${PhotoResponse.TABLE_NAME_PHOTO}")
    abstract fun deleteAll()

    @Query("SELECT MAX(indexInResponse) + 1 FROM ${PhotoResponse.TABLE_NAME_PHOTO} WHERE type = :type")
    abstract fun getNextIndexInType(type: Int): Int

    @Query("SELECT MAX(pageNumber) FROM ${PhotoResponse.TABLE_NAME_PHOTO} WHERE type = :type ORDER BY `pageNumber` ASC")
    abstract fun getNextPageInType(type: Int): Int
}