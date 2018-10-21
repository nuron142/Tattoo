package com.sunilk.tattoo.network.api.search

import com.google.gson.annotations.SerializedName
import com.sunilk.tattoo.network.api.search.Track

/**
 * Created by Sunil on 10/5/18.
 */
class TrackList {

    @SerializedName("items")
    val items: List<Track>? = null
}