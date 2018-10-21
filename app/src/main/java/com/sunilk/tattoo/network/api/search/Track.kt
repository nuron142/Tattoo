package com.sunilk.tattoo.network.api.search

import com.google.gson.annotations.SerializedName
import com.sunilk.tattoo.network.api.search.Album
import com.sunilk.tattoo.network.api.search.Artist

/**
 * Created by Sunil on 10/5/18.
 */
class Track {

    @SerializedName("album")
    val album: Album? = null

    @SerializedName("artists")
    val artists: List<Artist>? = null

    @SerializedName("id")
    val id: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("type")
    val type: String? = null

}