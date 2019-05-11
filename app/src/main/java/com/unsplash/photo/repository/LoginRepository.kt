package com.unsplash.photo.repository

import androidx.lifecycle.MutableLiveData
import com.unsplash.photo.UnsplashApp.Companion.ACCESS_KEY
import com.unsplash.photo.UnsplashApp.Companion.SECRET
import com.unsplash.photo.UnsplashApp.Companion.UNSPLASH_LOGIN_CALLBACK
import com.unsplash.photo.api.AuthorizeService
import com.unsplash.photo.extensions.addTo
import com.unsplash.photo.extensions.uiThread
import com.unsplash.photo.model.AccessTokenResponse
import com.unsplash.photo.repository.base.BaseRepository
import com.unsplash.photo.repository.base.ListingData
import com.unsplash.photo.repository.base.NetworkState
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: AuthorizeService) : BaseRepository() {
    val networkState = MutableLiveData<NetworkState>()
    val data = MutableLiveData<AccessTokenResponse>()
    fun onAccessToken(
        code: String
    ): ListingData<AccessTokenResponse> {
        networkState.postValue(NetworkState.LOADING)
        api.getAccessToken(
            client_id = ACCESS_KEY,
            client_secret = SECRET,
            redirect_uri = "wallsplash://".plus(UNSPLASH_LOGIN_CALLBACK),
            code = code,
            grant_type = "authorization_code"
        ).uiThread()
            .subscribe(
                {
                    networkState.postValue(NetworkState.LOADED)
                    data.postValue(it)
                },
                {
                    networkState.postValue(NetworkState.error(it.message))
                }
            )
            .addTo(compositeDisposable)
        return ListingData(
            data,
            networkState,
            networkState,
            refresh = {},
            retry = {}
        )
    }
}