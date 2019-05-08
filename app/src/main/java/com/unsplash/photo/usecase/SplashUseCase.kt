package com.unsplash.photo.usecase

import android.os.Parcelable
import com.unsplash.photo.base.usecase.BaseObservableUseCase
import io.reactivex.Observable
import kotlinx.android.parcel.Parcelize
import org.jsoup.Jsoup
import javax.inject.Inject


class SplashUseCase @Inject constructor() : BaseObservableUseCase() {
    override fun buildObservable(): Observable<*> {
        return parseUnsplash()
    }

    fun parseUnsplash(): Observable<SplashModel> {
        return Observable.create { observableEmitter ->
            val elements = Jsoup.connect("https://unsplash.com").get()
            if (elements.select("div.wqRmt ul li").isEmpty()) {
                observableEmitter.onError(Exception("Tag is null"))
            } else {
                val listTag = mutableListOf<String>()
                for (element in elements.select("div.wqRmt ul li")) {
                    listTag.add(element.select("a").text())
                }
                val urlImage =
                    elements.select("div._1wT8D").first().select("picture._2XHNB").select("img").first().absUrl("src")
                observableEmitter.onNext(SplashModel(listTag, urlImage))
                observableEmitter.onComplete()
            }
        }
    }

    @Parcelize
    data class SplashModel(
        var listTag: List<String>,
        var urlImage: String
    ) : Parcelable

}