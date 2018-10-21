package com.sunilk.tattoo.network

import com.sunilk.tattoo.network.api.artist.ArtistDetailResponse
import com.sunilk.tattoo.network.api.search.SearchResponse
import com.sunilk.tattoo.network.api.toptracks.ArtistTopAlbumsResponse
import io.reactivex.Flowable

/**
 * Created by Sunil on 10/7/18.
 */

interface INetworkService {

    fun setAccessToken(accessToken: String?)

    fun setNetworkChanged()

    fun getArtistDetailFlowable(artistID: String): Flowable<ArtistDetailResponse>

    fun getSearchQueryFlowable(query: String): Flowable<SearchResponse>

    fun getArtistTopAlbumsFlowable(artistID: String): Flowable<ArtistTopAlbumsResponse>

    fun subscribeNetworkChangeSubject(): Flowable<Boolean>

}