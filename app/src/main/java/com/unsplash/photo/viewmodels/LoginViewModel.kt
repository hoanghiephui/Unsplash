package com.unsplash.photo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.unsplash.photo.model.AccessTokenResponse
import com.unsplash.photo.repository.LoginRepository
import com.unsplash.photo.repository.base.NetworkState
import javax.inject.Inject

class LoginViewModel @Inject constructor(repository: LoginRepository) : BaseViewModel<LoginRepository>(repository) {
    private val code = MutableLiveData<String>()
    private val authenResult = Transformations.map(code) {
        repository.onAccessToken(it)
    }
    val authens: LiveData<AccessTokenResponse> = Transformations.switchMap(authenResult) { it.data }
    val networkState: LiveData<NetworkState> = Transformations.switchMap(authenResult) { it.networkState }

    fun onAccessToken(mCode: String): Boolean {
        if (code.value == mCode) {
            return false
        }
        code.value = mCode
        return true
    }
}