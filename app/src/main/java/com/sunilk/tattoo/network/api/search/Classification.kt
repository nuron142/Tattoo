package com.sunilk.tattoo.network.api.search

import com.google.gson.annotations.SerializedName

/**
 * Created by Sunil on 20/10/18.
 */
class Classification {

    @SerializedName("hashtags")
    val hashtags: List<String>? = null

    @SerializedName("styles")
    val styles: List<String>? = null
}