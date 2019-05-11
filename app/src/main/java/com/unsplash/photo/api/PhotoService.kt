package com.unsplash.photo.api

import com.unsplash.photo.model.LikePhotoResponse
import com.unsplash.photo.model.PhotoResponse
import io.reactivex.Observable
import retrofit2.http.*

interface PhotoService {
    @GET("photos")
    fun getListPhoto(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): Observable<List<PhotoResponse>>

    @POST("photos/{id}/like")
    fun likeAPhoto(
        @Path("id") id: String
    ): Observable<LikePhotoResponse>

    @DELETE("photos/{id}/like")
    fun unlikeAPhoto(
        @Path("id") id: String
    ): Observable<LikePhotoResponse>
}