package com.sunilk.tattoo.ui.activity.search

/**
 * Created by Sunil on 20/10/18.
 */
interface ITattooSearchActivityNavigator {

    fun openTattooDetailPage(tattooId: String)

    fun showError()

    fun showNoResultsFound()
}