package com.sunilk.tattoo.di

import android.content.Context
import com.sunilk.tattoo.network.INetworkService
import com.sunilk.tattoo.network.NetworkService
import com.sunilk.tattoo.network.TattooApiService
import com.sunilk.tattoo.ui.TattooApplication
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Sunil on 20/10/18.
 */

@Module
class AppModule {

    @Provides
    fun provideContext(tattooApplication: TattooApplication): Context {
        return tattooApplication.applicationContext
    }

    @Provides
    @Singleton
    fun provideOkHttp(context: Context): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, NetworkService.cacheSize))
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(tattooApiService: TattooApiService): INetworkService {
        return NetworkService(tattooApiService)
    }


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder().baseUrl(NetworkService.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): TattooApiService {
        return retrofit.create(TattooApiService::class.java)
    }
}