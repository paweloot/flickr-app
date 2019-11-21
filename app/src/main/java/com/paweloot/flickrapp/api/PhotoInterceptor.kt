package com.paweloot.flickrapp.api

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY = "e9179c205502f3a8b2d7c66d9c4fd1bf"

class PhotoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newUrl = originalRequest.url().newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
            .addQueryParameter("extras", "url_c,date_upload")
            .addQueryParameter("safesearch", "1")
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}