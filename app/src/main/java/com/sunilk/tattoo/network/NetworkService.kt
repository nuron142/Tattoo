package com.sunilk.tattoo.network

import android.content.Context
import com.sunilk.tattoo.network.api.artist.TattooDetailResponse
import com.sunilk.tattoo.network.api.search.TattooSearchResponse
import com.sunilk.tattoo.util.Utilities
import com.sunilk.tattoo.util.toClassData
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.Callable

/**
 * Created by Sunil on 20/10/18.
 */
class NetworkService : INetworkService {

    companion object {

        val TAG = NetworkService::class.java.simpleName

        const val BASE_URL = "https://backend-api.tattoodo.com/api"
        var cacheSize = 1 * 1024 * 1024L // 1 MB
    }

    private val context: Context
    private val okHttpClient: OkHttpClient

    private val networkChangeSubject = PublishProcessor.create<Boolean>()

    constructor(context: Context, okHttpClient: OkHttpClient) {

        this.context = context

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        this.okHttpClient = okHttpClient.newBuilder()
                .cache(Cache(context.cacheDir, cacheSize))
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    override fun setNetworkChanged() {

        networkChangeSubject.onNext(Utilities.isNetworkAvailable(context))
    }

    override fun getSearchQueryFlowable(query: String): Flowable<TattooSearchResponse> {

        return Flowable.fromCallable(Callable {

            val url = "$BASE_URL/v2/search/posts?q=$query"

            val request = Request.Builder().url(url).build()
            val response = okHttpClient.newCall(request).execute()

            return@Callable response.body()?.string()?.toClassData(TattooSearchResponse::class.java)
        })
    }

    override fun getTattooDetailFlowable(artistID: String): Flowable<TattooDetailResponse> {

        return Flowable.fromCallable(Callable {

            val url = "$BASE_URL/v2/posts/$artistID"

            val request = Request.Builder().url(url).build()
            val response = okHttpClient.newCall(request).execute()

            return@Callable response.body()?.string()?.toClassData(TattooDetailResponse::class.java)
        })
    }
}