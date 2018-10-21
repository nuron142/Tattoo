package com.sunilk.tattoo.network.api.search

import com.google.gson.annotations.SerializedName
import com.sunilk.tattoo.network.api.search.Album

/**
 * Created by Sunil on 10/5/18.
 */
class AlbumList {

    @SerializedName("items")
    val items: List<Album>? = null

}