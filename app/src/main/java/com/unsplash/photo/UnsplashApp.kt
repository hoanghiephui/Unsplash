package com.unsplash.photo

import com.unsplash.photo.di.components.AppComponent
import dagger.Lazy
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class UnsplashApp : DaggerApplication() {
    private val appComponent by lazy { AppComponent.getComponent(this) }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent
    @field:[Inject Named("api")]
    lateinit var retrofitApi: Lazy<Retrofit>

    @field:[Inject Named("napi")]
    lateinit var retrofitApiEx: Lazy<Retrofit>

    companion object {
        const val UNSPLASH_API_BASE_URL = "https://api.unsplash.com/"
        const val STREAM_API_BASE_URL = "https://api.getstream.io/"
        const val UNSPLASH_FOLLOWING_FEED_URL = "feeds/following"
        const val UNSPLASH_NODE_API_URL = ""
        const val UNSPLASH_URL = "https://unsplash.com/"
        const val UNSPLASH_JOIN_URL = "https://unsplash.com/join"
        const val UNSPLASH_SUBMIT_URL = "https://unsplash.com/submit"
        const val UNSPLASH_LOGIN_CALLBACK = "unsplash-auth-callback"

        const val DOWNLOAD_PATH = "/Pictures/Unsplash/"
        const val DOWNLOAD_PHOTO_FORMAT = ".jpg"
        const val DOWNLOAD_COLLECTION_FORMAT = ".zip"
        const val PAGE_SIZE = 12
        const val PRE_FETCH_SIZE = 12
        const val AUTHEN = "authen"
        const val SECRET = "57df2a9ff4a2a73ae4f8c119bb7d85e6c5c2fb8bebd1c08e0ef488412ec5e92b"
        const val ACCESS_KEY = "b0fc18a625db650671647a77a8f786f600658640d2598fade9a6ddad2d7c3ccb"
        const val ACCESS_TOKEN = "access_token"
    }
}