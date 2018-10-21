package com.sunilk.tattoo.network.api.artist

import com.google.gson.annotations.SerializedName
import com.sunilk.tattoo.network.api.search.TattooDetail

/**
 * Created by Sunil on 21/10/18.
 */
class TattooDetailResponse {

    @SerializedName("data")
    val tattooDetail: TattooDetail? = null
}