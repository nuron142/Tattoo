package com.sunilk.tattoo.ui.activity.tattoodetail

import android.app.Activity
import android.support.design.widget.Snackbar
import android.view.animation.DecelerateInterpolator
import com.sunilk.tattoo.R
import com.sunilk.tattoo.databinding.ActivityTattooDetailBinding

/**
 * Created by Sunil on 21/10/18.
 */

class TattooDetailActivityNavigator : ITattooDetailActivityNavigator {

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

        Snackbar.make(artistDetailBinding.root, activity.getString(R.string.general_error), Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun animateTextDetails() {

        artistDetailBinding.textDetailLayout.animate()
            .alpha(1f)
            .translationY(0f)
            .setInterpolator(DecelerateInterpolator())
            .setDuration(300)
            .setStartDelay(200)
            .start()
    }

}