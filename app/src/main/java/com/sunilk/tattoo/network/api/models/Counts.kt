package com.sunilk.tattoo.network.api.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Sunil on 20/10/18.
 */
class Counts {

    @SerializedName("posts")
    val posts: String? = null

    @SerializedName("mentions")
    val mentions: String? = null

    @SerializedName("comments")
    val comments: String? = null

    @SerializedName("following")
    val following: String? = null

    @SerializedName("followers")
    val followers: String? = null

    @SerializedName("likes")
    val likes: String? = null

    @SerializedName("pins")
    val pins: String? = null
}