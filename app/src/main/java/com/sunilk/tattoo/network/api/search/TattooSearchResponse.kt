package com.sunilk.tattoo.network.api.search

import com.google.gson.annotations.SerializedName

/**
 * Created by Sunil on 10/5/18.
 */
class TattooSearchResponse {

    @SerializedName("data")
    val tattooList: List<TattooDetail>? = null
}