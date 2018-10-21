package com.sunilk.tattoo.ui.activity.search

import com.sunilk.tattoo.R
import com.sunilk.tattoo.ui.activity.artistdetail.TattooDetailActivity
import android.app.Activity
import android.support.design.widget.Snackbar
import com.sunilk.tattoo.databinding.ActivityTattooSearchBinding

/**
 * Created by Sunil on 10/6/18.
 */
class TattooSearchTattooActivityNavigator : ITattooSearchActivityNavigator {

    private val activity: Activity
    private val tattooSearchBinding: ActivityTattooSearchBinding

    constructor(activity: Activity, tattooSearchBinding: ActivityTattooSearchBinding) {

        this.activity = activity
        this.tattooSearchBinding = tattooSearchBinding
    }

    override fun openTattooDetailPage(tattooId: String) {

        TattooDetailActivity.launch(activity, tattooId)
    }

    override fun showError() {

        Snackbar.make(tattooSearchBinding.root, activity.getString(R.string.general_error), Snackbar.LENGTH_SHORT).show()
    }

    override fun showNoResultsFound() {

        Snackbar.make(tattooSearchBinding.root, activity.getString(R.string.no_results_found), Snackbar.LENGTH_SHORT).show()
    }
}