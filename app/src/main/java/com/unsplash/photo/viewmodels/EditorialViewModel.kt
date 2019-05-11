package com.unsplash.photo.viewmodels

import android.util.SparseArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.unsplash.photo.UnsplashApp.Companion.PAGE_SIZE
import com.unsplash.photo.model.LikePhotoResponse
import com.unsplash.photo.model.PhotoResponse
import com.unsplash.photo.repository.EditorialRepository
import com.unsplash.photo.repository.base.NetworkState
import javax.inject.Inject

class EditorialViewModel @Inject constructor(
    editorialRepository: EditorialRepository
) : BaseViewModel<EditorialRepository>(editorialRepository) {
    private val typeName = MutableLiveData<Int>()
    private val repoResult = Transformations.map(typeName) {
        repository.getListPhoto(it, PAGE_SIZE)
    }
    val photoList: LiveData<PagedList<PhotoResponse>> = Transformations.switchMap(repoResult) { it.pagedList }
    val networkState: LiveData<NetworkState> = Transformations.switchMap(repoResult) { it.networkState }
    val refreshState: LiveData<NetworkState> = Transformations.switchMap(repoResult) { it.refreshState }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun showPhotos(type: Int): Boolean {
        if (typeName.value == type) {
            return false
        }
        typeName.value = type
        return true
    }

    fun retry() {
        val listing = repoResult?.value
        listing?.retry?.invoke()
    }

    fun currentType(): Int? = typeName.value

    private val idPhoto = MutableLiveData<SparseArray<Any>>()
    private val position = MutableLiveData<Int>()
    private val photo = MutableLiveData<PhotoResponse>()
    private val likeResult = Transformations.map(idPhoto) {
        repository.onLikeOrUnLikePhoto(it[0] as String, it[1] as Boolean)
    }
    val likePhoto: LiveData<LikePhotoResponse> = Transformations.switchMap(likeResult) { it.data }
    val networkLikeState: LiveData<NetworkState> = Transformations.switchMap(likeResult) { it.networkState }

    fun onLikePhoto(id: SparseArray<Any>, mPosition: Int, photoResponse: PhotoResponse): Boolean {
        if (idPhoto.value == id) {
            return false
        }
        photo.postValue(photoResponse)
        position.postValue(mPosition)
        idPhoto.value = id
        return true
    }

    fun onUpdatePhotoToDb(photoResponse: PhotoResponse) {
        repository.onUpdatePhotoDb(photoResponse)
    }

    fun currentPosition(): Int? = position.value
    fun currentPhoto(): PhotoResponse? = photo.value
}