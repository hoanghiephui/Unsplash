package com.unsplash.photo.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.unsplash.photo.UnsplashApp.Companion.PRE_FETCH_SIZE
import com.unsplash.photo.api.PhotoService
import com.unsplash.photo.db.d.UnsplashDatabase
import com.unsplash.photo.extensions.addTo
import com.unsplash.photo.extensions.uiThread
import com.unsplash.photo.model.LikePhotoResponse
import com.unsplash.photo.model.PhotoResponse
import com.unsplash.photo.model.PhotoResponse.Companion.TYPE_LATEST
import com.unsplash.photo.model.PhotoResponse.Companion.TYPE_OLDEST
import com.unsplash.photo.model.PhotoResponse.Companion.TYPE_POPULAR
import com.unsplash.photo.repository.base.BaseRepository
import com.unsplash.photo.repository.base.Listing
import com.unsplash.photo.repository.base.ListingData
import com.unsplash.photo.repository.base.NetworkState
import com.unsplash.photo.repository.callback.PhotoBoundaryCallback
import java.util.concurrent.Executor
import javax.inject.Inject

class EditorialRepository @Inject constructor(
    private val db: UnsplashDatabase,
    private val gson: Gson,
    private val api: PhotoService,
    private val networkPageSize: Int = PRE_FETCH_SIZE,
    private val ioExecutor: Executor
) : BaseRepository(), IEditorialRepository {

    override fun getListPhoto(type: Int, prePage: Int): Listing<PhotoResponse> {
        // create a boundary callback which will observe when the user reaches to the edges of
        // the list and update the database with extra data.
        val boundaryCallback = PhotoBoundaryCallback(
            type = type,
            webservice = api,
            handleResponse = this::insertResultIntoDb,
            ioExecutor = ioExecutor,
            networkPageSize = networkPageSize,
            compositeDisposable = compositeDisposable,
            db = db
        )
        // we are using a mutable live data to trigger refresh requests which eventually calls
        // refresh method and gets a new live data. Each refresh request by the user becomes a newly
        // dispatched data in refreshTrigger
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh(type)
        }

        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = db.getPhotoDao().getPhotos(type).toLiveData(
            pageSize = prePage,
            boundaryCallback = boundaryCallback
        )

        return Listing(
            pagedList = livePagedList,
            networkState = boundaryCallback.networkState,
            retry = {
                boundaryCallback.helper.retryAllFailed()
            },
            refresh = {
                refreshTrigger.value = null
            },
            refreshState = refreshState
        )
    }

    /**
     * Inserts the response into the database while also assigning position indices to items.
     */
    private fun insertResultIntoDb(type: Int, body: List<PhotoResponse>, pageNumber: Int) {
        body.let { posts ->
            db.runInTransaction {
                val start = db.getPhotoDao().getNextIndexInType(type)
                val items = posts.mapIndexed { index, child ->
                    child.indexInResponse = start + index
                    child
                }
                val list = gson.fromJson<List<PhotoResponse>>(
                    gson.toJson(items),
                    object : TypeToken<List<PhotoResponse>>() {}.type
                )
                val newList = list.asSequence().map { feeds ->
                    feeds.type = type
                    feeds.pageNumber = pageNumber
                    feeds
                }.toList()
                db.getPhotoDao().insert(newList)
            }
        }
    }

    /**
     * When refresh is called, we simply run a fresh network request and when it arrives, clear
     * the database table and insert all new items in a transaction.
     * <p>
     * Since the PagedList already uses a database bound data source, it will automatically be
     * updated after the database transaction is finished.
     */
    @MainThread
    private fun refresh(type: Int): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING
        api.getListPhoto(1, networkPageSize, onGetTypePhotoString(type))
            .uiThread()
            .subscribe(
                {
                    db.runInTransaction {
                        db.getPhotoDao().deleteAll(type)
                        insertResultIntoDb(type, it, 2)
                    }
                    // since we are in bg thread now, post the result.
                    networkState.postValue(NetworkState.LOADED)
                },
                {
                    // retrofit calls this on main thread so safe to call set value
                    networkState.value = NetworkState.error(it.message)
                }
            ).addTo(compositeDisposable)
        return networkState
    }

    fun onLikeOrUnLikePhoto(id: String,
                    isLike: Boolean): ListingData<LikePhotoResponse> {
        val networkState = MutableLiveData<NetworkState>()
        val dataState = MutableLiveData<LikePhotoResponse>()
        networkState.value = NetworkState.LOADING
        if (!isLike) {
            api.likeAPhoto(id)
                .uiThread()
                .distinctUntilChanged()
                .subscribe(
                    {
                        networkState.postValue(NetworkState.LOADED)
                        dataState.postValue(it)
                    },
                    {
                        networkState.postValue(NetworkState.error(it.message))
                    }
                )
                .addTo(compositeDisposable)
        } else {
            api.unlikeAPhoto(id)
                .uiThread()
                .distinctUntilChanged()
                .subscribe(
                    {
                        networkState.postValue(NetworkState.LOADED)
                        dataState.postValue(it)
                    },
                    {
                        networkState.postValue(NetworkState.error(it.message))
                    }
                )
                .addTo(compositeDisposable)
        }
        return ListingData(
            data = dataState,
            networkState = networkState,
            refreshState = networkState,
            retry = {},
            refresh = {}
        )
    }

    fun onUpdatePhotoDb(photoResponse: PhotoResponse) {
        db.runInTransaction {
            db.getPhotoDao().update(photoResponse)
        }
    }

    companion object {
        fun onGetTypePhotoString(type: Int): String {
            return when (type) {
                TYPE_LATEST -> {
                    "latest"
                }
                TYPE_OLDEST -> {
                    "oldest"
                }
                TYPE_POPULAR -> {
                    "popular"
                }
                else -> ""
            }
        }
    }
}

interface IEditorialRepository {
    fun getListPhoto(type: Int, prePage: Int): Listing<PhotoResponse>
}