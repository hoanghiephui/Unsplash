package com.unsplash.photo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unsplash.photo.db.d.UnsplashDatabase
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val db: UnsplashDatabase): ViewModel() {
    val scrollLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun onClearDb() {
        db.getPhotoDao().deleteAll()
    }
}