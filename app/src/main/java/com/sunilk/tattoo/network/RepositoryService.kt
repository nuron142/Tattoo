package com.sunilk.tattoo.network

import com.sunilk.tattoo.network.api.response.TattooDetailResponse
import com.sunilk.tattoo.network.api.response.TattooSearchResponse
import io.reactivex.Single

/**
 * Created by Sunil on 20/10/18.
 */

/**
 * Repository exposes methods to get search results
 * Currently it only uses network but database search can also added here
 */

class RepositoryService(private val tattooApiService: TattooApiService) : IRepositoryService {

    companion object {

        val TAG = RepositoryService::class.java.simpleName

        const val BASE_URL = "https://backend-api.tattoodo.com/api/"
        var cacheSize = 1 * 1024 * 1024L // 1 MB
    }

    override fun getSearchQuery(query: String, page: Int?): Single<TattooSearchResponse> {

        return tattooApiService.getTattooSearch(query, page)
    }

    override fun getTattooDetail(tattooID: String): Single<TattooDetailResponse> {

        return tattooApiService.getTattooDetail(tattooID)
    }
}