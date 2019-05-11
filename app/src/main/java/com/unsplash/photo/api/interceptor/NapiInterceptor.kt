package com.unsplash.photo.api.interceptor

import android.content.SharedPreferences
import com.unsplash.photo.UnsplashApp
import com.unsplash.photo.UnsplashApp.Companion.ACCESS_KEY
import com.unsplash.photo.UnsplashApp.Companion.ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NapiInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (sharedPreferences.getBoolean(UnsplashApp.AUTHEN, false)) {
            return chain.proceed(
                request.newBuilder()
                    .addHeader("authority", "unsplash.com")
                    .addHeader("method", "GET")
                    .addHeader("scheme", "https")
                    .addHeader(
                        "accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"
                    )
                    .addHeader("accept-encoding", "gzip, deflate, br")
                    .addHeader("accept-language", "en-US,en;q=0.9")
                    .addHeader("cache-control", "max-age=0")
                    .addHeader("upgrade-insecure-requests", "1")
                    .addHeader(
                        "user-agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36"
                    )
                    .addHeader(
                        "Authorization",
                        "Bearer " + sharedPreferences.getString(ACCESS_TOKEN, null)
                    )
                    .method(request.method(), request.body())
                    .build()
            )
        } else {
            return chain.proceed(
                request.newBuilder()
                    .addHeader("authority", "unsplash.com")
                    .addHeader("method", "GET")
                    .addHeader("scheme", "https")
                    .addHeader(
                        "accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"
                    )
                    .addHeader("accept-encoding", "gzip, deflate, br")
                    .addHeader("accept-language", "en-US,en;q=0.9")
                    .addHeader("cache-control", "max-age=0")
                    .addHeader("upgrade-insecure-requests", "1")
                    .addHeader(
                        "user-agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36"
                    )
                    .addHeader(
                        "Authorization",
                        "Client-ID " + ACCESS_KEY
                    )
                    .method(request.method(), request.body())
                    .build()
            )
        }
    }
}