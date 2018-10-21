package com.sunilk.tattoo.network.api.search

import com.google.gson.annotations.SerializedName

/**
 * Created by Sunil on 20/10/18.
 */
class Counts {

    @SerializedName("posts")
    val posts: Int? = null

    @SerializedName("mentions")
    val mentions: Int? = null

    @SerializedName("comments")
    val comments: Int? = null

    @SerializedName("following")
    val following: Int? = null

    @SerializedName("followers")
    val followers: Int? = null

    @SerializedName("likes")
    val likes: Int? = null

    @SerializedName("pins")
    val pins: Int? = null
}