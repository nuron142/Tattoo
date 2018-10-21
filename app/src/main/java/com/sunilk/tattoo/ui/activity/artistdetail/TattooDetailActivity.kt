package com.sunilk.tattoo.ui.activity.artistdetail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sunilk.tattoo.R
import com.sunilk.tattoo.databinding.ActivityTattooDetailBinding
import com.sunilk.tattoo.ui.TattooApplication

/**
 * Created by Sunil on 21/10/18.
 */

class TattooDetailActivity : AppCompatActivity() {

    companion object {

        val TAG = TattooDetailActivity::class.java.simpleName

        val TATTOO_ID = "artistId"

        fun launch(context: Context, tattooId: String) {

            val intent = Intent(context, TattooDetailActivity::class.java)
            intent.putExtra(TATTOO_ID, tattooId)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityTattooDetailBinding

    private lateinit var tattooDetailActivityViewModel: TattooDetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as TattooApplication).appComponent.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tattoo_detail)

        val tattooId = intent.getStringExtra(TATTOO_ID)

        tattooDetailActivityViewModel = TattooDetailActivityViewModel(tattooId, TattooDetailActivityService(this, binding))
        (application as TattooApplication).appComponent.inject(tattooDetailActivityViewModel)
        tattooDetailActivityViewModel.init()

        binding.vm = tattooDetailActivityViewModel
        binding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        tattooDetailActivityViewModel.onDestroy()
    }
}
