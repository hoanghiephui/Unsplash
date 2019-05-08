package com.unsplash.photo

import com.unsplash.photo.di.components.AppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class UnsplashApp : DaggerApplication() {
    private val appComponent by lazy { AppComponent.getComponent(this) }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    companion object {
        const val UNSPLASH_API_BASE_URL = "https://api.unsplash.com/"
        const val STREAM_API_BASE_URL = "https://api.getstream.io/"
        const val UNSPLASH_FOLLOWING_FEED_URL = "feeds/following"
        const val UNSPLASH_NODE_API_URL = ""
        const val UNSPLASH_URL = "https://unsplash.com/"
        const val UNSPLASH_JOIN_URL = "https://unsplash.com/join"
        const val UNSPLASH_SUBMIT_URL = "https://unsplash.com/submit"
        const val UNSPLASH_LOGIN_CALLBACK = "unsplash-auth-callback"

        const val DOWNLOAD_PATH = "/Pictures/Mysplash/"
        const val DOWNLOAD_PHOTO_FORMAT = ".jpg"
        const val DOWNLOAD_COLLECTION_FORMAT = ".zip"
    }
}