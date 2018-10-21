package com.sunilk.tattoo.di

import com.sunilk.tattoo.network.INetworkService
import com.sunilk.tattoo.network.NetworkService
import com.sunilk.tattoo.ui.TattooApplication
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by Sunil on 20/10/18.
 */

@Module
class AppModule {

    private val tattooApplication: TattooApplication

    constructor(tattooApplication: TattooApplication) {
        this.tattooApplication = tattooApplication
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
        return NetworkService(tattooApplication, okHttpClient)
    }
}