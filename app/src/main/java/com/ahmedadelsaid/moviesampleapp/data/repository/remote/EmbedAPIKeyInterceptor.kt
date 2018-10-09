package com.ahmedadelsaid.moviesampleapp.data.repository.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * EmbedAPIKeyInterceptor is an Interceptor class that making some automation like sending api_key to every api request by default.
 */

class EmbedAPIKeyInterceptor
constructor(private var settings: SettingsAPI) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", settings.apiKey)
                .build()

        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}