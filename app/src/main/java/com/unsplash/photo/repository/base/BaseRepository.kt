package com.unsplash.photo.repository.base

import io.reactivex.disposables.CompositeDisposable

abstract class BaseRepository {
    lateinit var compositeDisposable: CompositeDisposable
}