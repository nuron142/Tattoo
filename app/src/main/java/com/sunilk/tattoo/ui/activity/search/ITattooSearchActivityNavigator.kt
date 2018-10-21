package com.sunilk.tattoo.ui.activity.search

/**
 * Created by Sunil on 10/6/18.
 */
interface ITattooSearchActivityNavigator {

    fun openTattooDetailPage(tattooId: String)

    fun showError()

    fun showNoResultsFound()
}