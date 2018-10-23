package com.sunilk.tattoo.network

import com.sunilk.tattoo.network.api.response.TattooDetailResponse
import com.sunilk.tattoo.network.api.response.TattooSearchResponse
import io.reactivex.Single

/**
 * Created by Sunil on 20/10/18.
 */

interface IRepositoryService {

    fun getSearchQuery(query: String, page: Int?): Single<TattooSearchResponse>

    fun getTattooDetail(tattooID: String): Single<TattooDetailResponse>
}