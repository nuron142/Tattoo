package com.sunilk.tattoo.network.api.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Sunil on 20/10/18.
 */
class TattooDetail {

    @SerializedName("id")
    val id: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("image")
    val image: TattooImage? = null

    @SerializedName("counts")
    val counts: Counts? = null

    @SerializedName("artist")
    val artist: Artist? = null

    @SerializedName("shop")
    val shop: Shop? = null

    @SerializedName("classification")
    val classification: Classification? = null

}