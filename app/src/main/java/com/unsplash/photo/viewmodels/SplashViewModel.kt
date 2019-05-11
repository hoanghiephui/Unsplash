package com.unsplash.photo.viewmodels

import androidx.lifecycle.MutableLiveData
import com.unsplash.photo.repository.EditorialRepository
import com.unsplash.photo.repository.base.NetworkState
import com.unsplash.photo.usecase.SplashUseCase
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val useCase: SplashUseCase,
    repository: EditorialRepository
) : BaseViewModel<EditorialRepository>(repository) {
    val data: MutableLiveData<SplashUseCase.SplashModel> = MutableLiveData()
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    fun loadSplash() {
        add(
            callApi(useCase.buildObservable())
                .subscribe({
                    data.postValue(it as SplashUseCase.SplashModel?)
                }, {
                    networkState.postValue(NetworkState.error(it.message))
                    it.printStackTrace()
                })
        )
    }
}