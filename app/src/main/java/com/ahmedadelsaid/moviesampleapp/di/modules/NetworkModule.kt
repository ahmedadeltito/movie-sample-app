package com.ahmedadelsaid.moviesampleapp.di.modules

import com.ahmedadelsaid.moviesampleapp.di.scopes.MovieApplicationScope
import com.ahmedadelsaid.moviesampleapp.data.repository.remote.EmbedAPIKeyInterceptor
import com.ahmedadelsaid.moviesampleapp.data.repository.remote.MovieAPI
import com.ahmedadelsaid.moviesampleapp.data.repository.remote.SettingsAPI
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Network Module that provides Retrofit stuff.
 */

@Module
class NetworkModule {

    @Provides
    internal fun provideHttpClient(settings: SettingsAPI, keyInterceptor: EmbedAPIKeyInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(settings.timeout, TimeUnit.MILLISECONDS)
        httpClient.writeTimeout(settings.timeout, TimeUnit.MILLISECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(keyInterceptor)

        return httpClient.build()
    }

    @Provides
    internal fun provideApiKeyInterceptor(settings: SettingsAPI) =
            EmbedAPIKeyInterceptor(settings)

    @MovieApplicationScope
    @Provides
    internal fun provideRetrofit(httpClient: OkHttpClient, settings: SettingsAPI): MovieAPI {
        return Retrofit.Builder()
                .baseUrl(settings.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(MovieAPI::class.java)
    }

}