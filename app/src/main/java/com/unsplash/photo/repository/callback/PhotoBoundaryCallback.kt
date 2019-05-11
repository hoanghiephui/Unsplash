package com.unsplash.photo.repository.callback

import android.util.Log
import androidx.annotation.MainThread
import androidx.paging.PagedList
import androidx.paging.PagingRequestHelper
import com.unsplash.photo.api.PhotoService
import com.unsplash.photo.db.d.UnsplashDatabase
import com.unsplash.photo.extensions.addTo
import com.unsplash.photo.extensions.createStatusLiveData
import com.unsplash.photo.extensions.uiThread
import com.unsplash.photo.model.PhotoResponse
import com.unsplash.photo.repository.EditorialRepository
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor

/**
 * This boundary callback gets notified when user reaches to the edges of the list such that the
 * database cannot provide any more data.
 * <p>
 * The boundary callback might be called multiple times for the same direction so it does its own
 * rate limiting using the PagingRequestHelper class.
 */
class PhotoBoundaryCallback(
    private val type: Int,
    private val webservice: PhotoService,
    private val handleResponse: (Int, List<PhotoResponse>, Int) -> Unit,
    private val ioExecutor: Executor,
    private val networkPageSize: Int,
    private val compositeDisposable: CompositeDisposable,
    private val db: UnsplashDatabase
) : PagedList.BoundaryCallback<PhotoResponse>() {
    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()
    var page = 1

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { callback ->
            webservice.getListPhoto(
                page = page,
                perPage = networkPageSize,
                orderBy = EditorialRepository.onGetTypePhotoString(type)
            )
                .uiThread()
                .subscribe({
                    page++
                    insertItemsIntoDb(it, callback, page)
                }, {
                    callback.recordFailure(it)
                })
                .addTo(compositeDisposable)
        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: PhotoResponse) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { callback ->
            val pageNumber = db.getPhotoDao().getNextPageInType(type)
            Log.d("END", pageNumber.toString())
            webservice.getListPhoto(
                page = pageNumber,
                perPage = networkPageSize,
                orderBy = EditorialRepository.onGetTypePhotoString(type)
            )
                .uiThread()
                .subscribe({
                    insertItemsIntoDb(it, callback, pageNumber +1)
                }, {
                    callback.recordFailure(it)
                })
                .addTo(compositeDisposable)
        }
    }

    /**
     * every time it gets new items, boundary callback simply inserts them into the database and
     * paging library takes care of refreshing the list if necessary.
     */
    private fun insertItemsIntoDb(
        response: List<PhotoResponse>,
        it: PagingRequestHelper.Request.Callback,
        page: Int
    ) {
        ioExecutor.execute {
            handleResponse(type, response, page)
            it.recordSuccess()
        }
    }
}