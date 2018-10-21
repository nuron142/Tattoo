package com.sunilk.tattoo.ui.activity.artistdetail

import com.sunilk.tattoo.R
import android.app.Activity
import android.support.design.widget.Snackbar
import com.sunilk.tattoo.databinding.ActivityTattooDetailBinding

/**
 * Created by Sunil on 21/10/18.
 */

class TattooDetailActivityService : ITattooDetailActivityService {

    private val activity: Activity
    private val artistDetailBinding: ActivityTattooDetailBinding

    constructor(activity: Activity, artistDetailBinding: ActivityTattooDetailBinding) {

        this.activity = activity
        this.artistDetailBinding = artistDetailBinding
    }

    override fun closeArtistDetail() {

        activity.finish()
    }

    override fun showError() {

        Snackbar.make(artistDetailBinding.root, activity.getString(R.string.general_error), Snackbar.LENGTH_SHORT).show()
    }

}