package com.sunilk.tattoo.di

import android.content.Context
import com.sunilk.tattoo.network.IRepositoryService
import com.sunilk.tattoo.network.RepositoryService
import com.sunilk.tattoo.network.TattooApiService
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
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(context: Context): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, RepositoryService.cacheSize))
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(tattooApiService: TattooApiService): IRepositoryService {
        return RepositoryService(tattooApiService)
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder().baseUrl(RepositoryService.BASE_URL)
            .client(okHttpClient)
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