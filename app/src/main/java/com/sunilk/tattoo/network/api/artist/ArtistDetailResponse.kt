package com.sunilk.tattoo.network.api.artist

import com.sunilk.tattoo.network.api.search.SpotifyImage
import com.google.gson.annotations.SerializedName

/**
 * Created by Sunil on 10/5/18.
 */
class ArtistDetailResponse {

    @SerializedName("id")
    val id: String? = null

    @SerializedName("images")
    val images: List<SpotifyImage>? = null

    @SerializedName("genres")
    val genres: List<String>? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("type")
    val type: String? = null
}