package com.unsplash.photo.extensions

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Maybe<T>.toObservableDistinct(): Observable<T> = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .toObservable()
    .distinctUntilChanged()

fun <T> Flowable<T>.toObservableDistinct(): Observable<T> = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .toObservable()
    .distinctUntilChanged()

fun <T> Maybe<T>.toObservable(): Observable<T> = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .toObservable()

fun <T> Observable<T>.uiThread() = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.uiThreadDistinct() = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .distinctUntilChanged()

fun Completable.uiThread() = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.uiThread() = this.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun Disposable.addTo(autoDisposable: CompositeDisposable) {
    autoDisposable.add(this)
}