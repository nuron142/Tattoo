package com.sunilk.tattoo.di

import com.sunilk.tattoo.network.INetworkService
import com.sunilk.tattoo.network.NetworkService
import com.sunilk.tattoo.ui.SpectreApplication
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by Sunil on 10/1/18.
 */

@Module
class AppModule {

    private val spectreApplication: SpectreApplication

    constructor(spectreApplication: SpectreApplication) {
        this.spectreApplication = spectreApplication
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(okHttpClient: OkHttpClient): INetworkService {
        return NetworkService(spectreApplication, okHttpClient)
    }
}