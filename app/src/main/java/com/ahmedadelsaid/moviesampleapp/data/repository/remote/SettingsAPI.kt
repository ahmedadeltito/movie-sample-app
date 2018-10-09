package com.ahmedadelsaid.moviesampleapp.data.repository.remote

import com.ahmedadelsaid.moviesampleapp.BuildConfig
import javax.inject.Inject

/**
 * Sample class that has the Api Key, Base URL and Timeout for Retrofit.
 */

class SettingsAPI
@Inject
constructor() {
    val apiKey: String
        get() = BuildConfig.API_KEY

    val baseUrl: String
        get() = BuildConfig.BASE_URL

    val timeout: Long
        get() = 30000
}