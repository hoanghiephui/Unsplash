package com.unsplash.photo.api.interceptor

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response

abstract class ReportExceptionInterceptor: Interceptor {

    fun handleException(e: Exception) {
        e.printStackTrace()
    }

    fun nullResponse(request: Request): Response {
        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_2)
            .code(400)
            .message("Handle an error.")
            .body(null)
            .build()
    }
}
