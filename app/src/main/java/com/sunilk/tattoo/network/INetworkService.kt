package com.sunilk.tattoo.network

import com.sunilk.tattoo.network.api.artist.TattooDetailResponse
import com.sunilk.tattoo.network.api.search.TattooSearchResponse
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Sunil on 20/10/18.
 */

interface INetworkService {

    fun getTattooDetailFlowable(tattooID: String): Single<TattooDetailResponse>

    fun getSearchQueryFlowable(query: String): Single<TattooSearchResponse>
}