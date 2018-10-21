package com.sunilk.tattoo.ui.activity.search

/**
 * Created by Sunil on 10/6/18.
 */
interface ISearchActivityService {

    fun openArtistDetailPage(artistId: String)

    fun showError()

    fun showNoResultsFound()
}