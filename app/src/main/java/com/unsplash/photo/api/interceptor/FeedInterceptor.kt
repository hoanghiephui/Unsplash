package com.unsplash.photo.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class FeedInterceptor : ReportExceptionInterceptor() {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer " + "")
            .build()
        try {
            return chain.proceed(request)
        } catch (e: Exception) {
            handleException(e)
            return nullResponse(request)
        }

    }
}