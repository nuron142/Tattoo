package com.sunilk.tattoo.ui.activity.tattoodetail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.animation.DecelerateInterpolator
import com.sunilk.tattoo.R
import com.sunilk.tattoo.databinding.ActivityTattooDetailBinding
import com.sunilk.tattoo.ui.activity.search.TattooSearchActivity
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Sunil on 21/10/18.
 */

class TattooDetailActivity : DaggerAppCompatActivity() {

    companion object {

        val TAG = TattooDetailActivity::class.java.simpleName

        const val TATTOO_ID = "artistId"

        fun launch(context: Context, tattooId: String) {

            val intent = Intent(context, TattooDetailActivity::class.java)
            intent.putExtra(TATTOO_ID, tattooId)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var tattooDetailActivityViewModel: TattooDetailActivityViewModel

    private lateinit var binding: ActivityTattooDetailBinding

    private val allSubscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tattoo_detail)

        binding.vm = tattooDetailActivityViewModel
        binding.executePendingBindings()

        subscribeToViewModel()

    }

    private fun subscribeToViewModel() {

        allSubscriptions.add(
            tattooDetailActivityViewModel.animateTextDetailsSubject.hide()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    animateTextDetails()
                }, { e -> Log.e(TattooSearchActivity.TAG, "Error : " + e.message) })
        )

        allSubscriptions.add(
            tattooDetailActivityViewModel.showErrorSubject.hide()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    Snackbar.make(binding.root, getString(R.string.general_error), Snackbar.LENGTH_SHORT).show()
                }, { e -> Log.e(TattooSearchActivity.TAG, "Error : " + e.message) })
        )

        allSubscriptions.add(
            tattooDetailActivityViewModel.closeArtistDetailSubject.hide()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    this@TattooDetailActivity.finish()
                }, { e -> Log.e(TattooSearchActivity.TAG, "Error : " + e.message) })
        )
    }


    private fun animateTextDetails() {

        binding.textDetailLayout.animate()
            .alpha(1f)
            .translationY(0f)
            .setInterpolator(DecelerateInterpolator())
            .setDuration(300)
            .setStartDelay(200)
            .start()
    }

    override fun onDestroy() {
        super.onDestroy()

        allSubscriptions.dispose()
        tattooDetailActivityViewModel.onDestroy()
    }

}
