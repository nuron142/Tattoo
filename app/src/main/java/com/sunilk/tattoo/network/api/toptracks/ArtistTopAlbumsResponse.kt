package com.sunilk.tattoo.network.api.toptracks

import com.sunilk.tattoo.network.api.search.Album
import com.google.gson.annotations.SerializedName

/**
 * Created by Sunil on 10/5/18.
 */

class ArtistTopAlbumsResponse {

    @SerializedName("items")
    val items: List<Album>? = null

}