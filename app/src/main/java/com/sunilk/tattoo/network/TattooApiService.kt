package com.sunilk.tattoo.network

import com.sunilk.tattoo.network.api.response.TattooDetailResponse
import com.sunilk.tattoo.network.api.response.TattooSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Sunil on 20/10/18.
 */

interface TattooApiService {

    @GET("v2/search/posts")
    fun getTattooSearch(@Query("q") searchQuery: String,
                        @Query("page") page: Int?): Single<TattooSearchResponse>


    @GET("v2/posts/{tattooID}")
    fun getTattooDetail(@Path("tattooID") tattooID: String): Single<TattooDetailResponse>
}