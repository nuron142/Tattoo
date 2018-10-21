package com.sunilk.tattoo.ui.activity.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.sunilk.tattoo.R
import com.sunilk.tattoo.databinding.ActivityTattooSearchBinding
import com.sunilk.tattoo.ui.TattooApplication
import com.sunilk.tattoo.ui.adapter.BindingRecyclerAdapter
import com.sunilk.tattoo.util.Utilities
import com.sunilk.tattoo.util.itemanimators.AlphaCrossFadeAnimator


/**
 * Created by Sunil on 20/10/18.
 */
class TattooSearchActivity : AppCompatActivity() {

    companion object {

        val TAG: String = TattooSearchActivity::class.java.simpleName

        fun launch(activity: Activity) {

            val intent = Intent(activity, TattooSearchActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityTattooSearchBinding

    private lateinit var tattooSearchActivityViewModel: TattooSearchActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tattoo_search)

        tattooSearchActivityViewModel = TattooSearchActivityViewModel(TattooSearchTattooActivityNavigator(this, binding))
        (application as TattooApplication).appComponent.inject(tattooSearchActivityViewModel)
        tattooSearchActivityViewModel.init()

        binding.vm = tattooSearchActivityViewModel
        binding.executePendingBindings()

        setupRecyclerView()

        Utilities.showKeyBoard(this, binding.searchEditText)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupRecyclerView() {

        val itemAnimator = AlphaCrossFadeAnimator()

        itemAnimator.addDuration = 200
        itemAnimator.removeDuration = 200
        itemAnimator.changeDuration = 200
        itemAnimator.moveDuration = 200

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = BindingRecyclerAdapter(tattooSearchActivityViewModel.dataSet, tattooSearchActivityViewModel.viewModelLayoutIdMap)

        binding.recyclerView.itemAnimator = itemAnimator
        binding.recyclerView.adapter = adapter

        binding.recyclerView.setOnTouchListener { v, e ->

            Utilities.hideKeyBoard(this, binding.recyclerView)
            return@setOnTouchListener false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tattooSearchActivityViewModel.onDestroy()
    }
}
