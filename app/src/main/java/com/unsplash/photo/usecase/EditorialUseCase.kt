package com.unsplash.photo.usecase

import com.unsplash.photo.base.usecase.BaseObservableUseCase
import com.unsplash.photo.model.PhotoResponse
import com.unsplash.photo.repository.EditorialRepository
import io.reactivex.Observable
import javax.inject.Inject

class EditorialUseCase @Inject constructor(private val editorialRepository: EditorialRepository) :
    BaseObservableUseCase() {
    var page: Int = 1
    override fun buildObservable(): Observable<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}