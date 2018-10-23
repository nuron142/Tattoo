package com.sunilk.tattoo.network.api.response

import com.google.gson.annotations.SerializedName
import com.sunilk.tattoo.network.api.models.TattooDetail

/**
 * Created by Sunil on 21/10/18.
 */
class TattooDetailResponse {

    @SerializedName("data")
    val tattooDetail: TattooDetail? = null
}