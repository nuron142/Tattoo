package com.sunilk.tattoo.network.api.response

import com.google.gson.annotations.SerializedName
import com.sunilk.tattoo.network.api.models.Meta
import com.sunilk.tattoo.network.api.models.TattooDetail

/**
 * Created by Sunil on 20/10/18.
 */
class TattooSearchResponse {

    @SerializedName("data")
    val tattooList: List<TattooDetail>? = null

    @SerializedName("meta")
    val meta: Meta? = null
}