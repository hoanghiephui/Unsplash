package com.unsplash.photo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.unsplash.photo.R
import com.unsplash.photo.extensions.uiThread
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var gson: Gson
    val error = MutableLiveData<AppErrors>()
    val progress = MutableLiveData<Boolean>()
    val logoutProcess = MutableLiveData<Boolean>()
    val counter = MutableLiveData<Int>()
    private val disposable = CompositeDisposable()

    protected fun add(disposable: Disposable) = this.disposable.add(disposable)

    protected fun disposeAll() = disposable.clear()

    private fun handleError(throwable: Throwable) {
        hideProgress()
        if (throwable is HttpException) {
            val response = throwable.response()
            val message: String? = JSONObject(response.errorBody()?.string() ?: "").getString("message")
            val code = response.code()
            if (code == 401) { // OTP
                val twoFactor = response.headers()["X-GitHub-OTP"]
                if (twoFactor != null) {
                    error.postValue(AppErrors(AppErrors.ErrorType.TWO_FACTOR, message = message
                        ?: response.message()))
                } else {
                    error.postValue(AppErrors(AppErrors.ErrorType.OTHER, resId = R.string.failed_login, message = message))
                }
            } else {
                error.postValue(AppErrors(AppErrors.ErrorType.OTHER, resId = R.string.network_error, message = message))
            }
            return
        }
        error.postValue(AppErrors(AppErrors.ErrorType.OTHER, resId = getErrorResId(throwable)))
    }

    protected fun showProgress() {
        progress.postValue(true)
    }

    protected fun hideProgress() {
        progress.postValue(false)
    }

    protected fun postCounter(count: Int) {
        counter.postValue(count)
    }

    protected fun <T> callApi(observable: Observable<T>): Observable<T> = observable
        .uiThread()
        .doOnSubscribe { showProgress() }
        .doOnNext { hideProgress() }
        .doOnError { handleError(it) }
        .doOnComplete { hideProgress() }

    protected fun callApi(completable: Completable): Completable = completable
        .uiThread()
        .doOnSubscribe { showProgress() }
        .doOnComplete { hideProgress() }
        .doOnError { handleError(it) }
        .doOnComplete { hideProgress() }

    protected fun <T> justSubscribe(observable: Observable<T>) = add(callApi(observable).subscribe({}, { it.printStackTrace() }))
    protected fun justSubscribe(completable: Completable) = add(callApi(completable).subscribe({}, { it.printStackTrace() }))

    override fun onCleared() {
        super.onCleared()
        disposeAll()
    }

    private fun getErrorResId(throwable: Throwable): Int = when (throwable) {
        is IOException -> R.string.request_error
        is TimeoutException -> R.string.unexpected_error
        else -> R.string.network_error
    }
}

data class ValidationError(val fieldType: FieldType,
                           val isValid: Boolean = false) {
    enum class FieldType {
        USERNAME, PASSWORD, TWO_FACTOR, URL
    }
}

data class AppErrors(val errorType: ErrorType = ErrorType.CONNECTION,
                     val message: String? = null,
                     val resId: Int? = null) {
    enum class ErrorType {
        TWO_FACTOR, CONNECTION, TIME_OUT, OTHER
    }
}