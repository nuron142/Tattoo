package com.sunilk.tattoo.network.api.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Sunil on 20/10/18.
 */
class Artist {

    @SerializedName("id")
    val id: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("username")
    val username: String? = null

    @SerializedName("image_url")
    val image_url: String? = null

    @SerializedName("counts")
    val counts: Counts? = null
}