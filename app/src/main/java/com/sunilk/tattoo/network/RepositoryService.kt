package com.sunilk.tattoo.network

import com.sunilk.tattoo.network.api.response.TattooDetailResponse
import com.sunilk.tattoo.network.api.response.TattooSearchResponse
import io.reactivex.Single

/**
 * Created by Sunil on 20/10/18.
 */
class RepositoryService : IRepositoryService {

    companion object {

        val TAG = RepositoryService::class.java.simpleName

        const val BASE_URL = "https://backend-api.tattoodo.com/api/"
        var cacheSize = 1 * 1024 * 1024L // 1 MB
    }

    private val tattooApiService: TattooApiService

    constructor(tattooApiService: TattooApiService) {

        this.tattooApiService = tattooApiService
    }

    override fun getSearchQueryFlowable(query: String): Single<TattooSearchResponse> {

        return tattooApiService.getTattooSearch(query)
    }

    override fun getTattooDetailFlowable(tattooID: String): Single<TattooDetailResponse> {

        return tattooApiService.getTattooDetail(tattooID)
    }
}