package com.sunilk.tattoo.ui.activity.artistdetail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sunilk.tattoo.R
import com.sunilk.tattoo.databinding.ActivityTattooDetailBinding
import com.sunilk.tattoo.ui.SpectreApplication

/**
 * Created by Sunil on 10/4/18.
 */

class ArtistDetailActivity : AppCompatActivity() {

    companion object {

        val TAG = ArtistDetailActivity::class.java.simpleName

        val ARTIST_ID = "artistId"

        fun launch(context: Context, artistId: String) {

            val intent = Intent(context, ArtistDetailActivity::class.java)
            intent.putExtra(ARTIST_ID, artistId)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityTattooDetailBinding

    private lateinit var artistDetailActivityViewModel: ArtistDetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as SpectreApplication).appComponent.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tattoo_detail)

        val artistID = intent.getStringExtra(ARTIST_ID)

        artistDetailActivityViewModel = ArtistDetailActivityViewModel(artistID, ArtistDetailActivityService(this, binding))
        (application as SpectreApplication).appComponent.inject(artistDetailActivityViewModel)
        artistDetailActivityViewModel.init()

        binding.vm = artistDetailActivityViewModel
        binding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        artistDetailActivityViewModel.onDestroy()
    }
}
