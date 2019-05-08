package com.unsplash.photo.viewmodels

import androidx.lifecycle.MutableLiveData
import com.unsplash.photo.usecase.SplashUseCase
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val useCase: SplashUseCase) : BaseViewModel() {
    val data: MutableLiveData<SplashUseCase.SplashModel> = MutableLiveData()
    fun loadSplash() {
        add(
            callApi(useCase.buildObservable())
                .subscribe({
                    data.postValue(it as SplashUseCase.SplashModel?)
                }, ::println)
        )
    }
}