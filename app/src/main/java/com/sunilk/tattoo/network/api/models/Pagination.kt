package com.sunilk.tattoo.network.api.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Sunil on 20/10/18.
 */

class Pagination {

    @SerializedName("current_page")
    val current_page: Int? = null

    @SerializedName("total_pages")
    val total_pages: Int? = null
}