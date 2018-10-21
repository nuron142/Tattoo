package com.sunilk.tattoo.network

import com.sunilk.tattoo.network.api.artist.TattooDetailResponse
import com.sunilk.tattoo.network.api.search.TattooSearchResponse
import io.reactivex.Flowable

/**
 * Created by Sunil on 20/10/18.
 */

interface INetworkService {

    fun setNetworkChanged()

    fun getTattooDetailFlowable(artistID: String): Flowable<TattooDetailResponse>

    fun getSearchQueryFlowable(query: String): Flowable<TattooSearchResponse>
}