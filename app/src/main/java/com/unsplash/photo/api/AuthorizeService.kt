package com.unsplash.photo.api

import com.unsplash.photo.model.AccessTokenResponse
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthorizeService {
    @POST("oauth/token")
    fun getAccessToken(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("redirect_uri") redirect_uri: String,
        @Query("code") code: String,
        @Query("grant_type") grant_type: String
    ): Observable<AccessTokenResponse>
}