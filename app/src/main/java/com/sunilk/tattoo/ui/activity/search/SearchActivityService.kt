package com.sunilk.tattoo.ui.activity.search

import com.sunilk.tattoo.R
import com.sunilk.tattoo.databinding.ActivitySearchBinding
import com.sunilk.tattoo.ui.activity.artistdetail.ArtistDetailActivity
import android.app.Activity
import android.support.design.widget.Snackbar
import com.sunilk.tattoo.ui.activity.search.ISearchActivityService

/**
 * Created by Sunil on 10/6/18.
 */
class SearchActivityService : ISearchActivityService {

    private val activity: Activity
    private val searchActivityBinding: ActivitySearchBinding

    constructor(activity: Activity, searchActivityBinding: ActivitySearchBinding) {

        this.activity = activity
        this.searchActivityBinding = searchActivityBinding
    }

    override fun openArtistDetailPage(artistId: String) {

        ArtistDetailActivity.launch(activity, artistId)
    }

    override fun showError() {

        Snackbar.make(searchActivityBinding.root, activity.getString(R.string.general_error), Snackbar.LENGTH_SHORT).show()
    }

    override fun showNoResultsFound() {

        Snackbar.make(searchActivityBinding.root, activity.getString(R.string.no_results_found), Snackbar.LENGTH_SHORT).show()
    }
}