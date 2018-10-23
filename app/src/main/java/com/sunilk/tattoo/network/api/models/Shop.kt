package com.sunilk.tattoo.network.api.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Sunil on 20/10/18.
 */
class Shop {

    @SerializedName("id")
    val id: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("username")
    val username: String? = null

    @SerializedName("address")
    val address: ShopAddress? = null
}